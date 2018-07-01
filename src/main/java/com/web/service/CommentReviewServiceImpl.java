package com.web.service;

import com.web.data.mapper.CommentReviewMapper;
import com.web.data.pojo.CommentReview;
import com.web.data.pojo.SysUser;
import com.web.utils.PropertyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("commentReviewService")
@PropertySource(value = "classpath:comment-review-category.properties")
public class CommentReviewServiceImpl implements ICommentReviewService {

    private List<String> list;

    @Value("#{'${comment.review.category}'.split(',')}")
    private String[] commmentCats;   // could also be a List<String>

    @Resource
    private CommentReviewMapper commentReviewMapper;

    public Map<String, Object> getCommentReview(String lessonId, String scheduleId) {

        Map<String, Object> reviewMap = new HashMap<>();

        for (String str : commmentCats) {

            String label = PropertyUtil.getProperty(str);

            String[] result = str.split("-");
            String source = result[0];
            String target = result[1];
            Map<String, Object> map = new HashMap<>();
            map.put("lessonId", lessonId);
            if (scheduleId != null && scheduleId.trim().length() > 0) {
                map.put("schedule", scheduleId);
            }

            map.put("source", source);
            map.put("target", target);

            //获取某种类型下，针对某课程或者日程的评价
            List<CommentReview> crList = commentReviewMapper.getCommentReview(map);
            List<List<String>> root = null;
            int calcIndex = 0;
            if (crList != null && crList.size() > 0) {
                root = new ArrayList<>();
                List<String> comments = new ArrayList<>();
                comments.add(" ");
                root.add(comments);
                Set<String> ids = new HashSet<>();
                List<String> row = null;
                for (CommentReview cr: crList) {
                    String userId = cr.getUserId();
                    SysUser user = cr.getUser();
                    if (!ids.contains(userId)) {

                        ids.add(userId);
                        row = new ArrayList<>();
                        root.add(row);
                        String head = user.getUserName() + "(" + user.getPhone() +")";
                        row.add(head);
                    }

                    if (ids.size() == 1) {
                        if (cr.getCommentType() == 1) {
                            calcIndex++;
                        }
                        comments.add(cr.getCommentCategory());

                    }
                    if (cr.getCommentType() == 1) {
                        row.add(String.valueOf(cr.getScore()));
                    }

                    if (cr.getCommentType() == 2) {
                        row.add(cr.getComment());
                    }
                }
                /*comments.add("平均分");
                comments.add("总评价分");
                //comments.add("评语");
                row.add(averageScore(scores));
                row.add(sumScore(scores));*/
            }

            if (!isListEmpty(root) && calcIndex > 0) {
                List<String> comments = root.get(0);
                comments.add(calcIndex + 1, "平均分");
                comments.add(calcIndex + 2, "评价总分");
                for (int i = 1; i < root.size(); i++) {
                    List<String> row = root.get(i);
                    float score = 0;
                    for (int j = 1; j <= calcIndex; j++) {
                        score += Integer.valueOf(row.get(j));
                    }
                    row.add(calcIndex + 1, String.format("%.1f", score/calcIndex));
                    row.add(calcIndex + 2, String.format("%.0f", score));
                }
            }


            List<List<String>> trans = transformReview(root);

            reviewMap.put(label, trans);


        }
        return reviewMap;
    }

    private boolean isListEmpty(List list) {
        if (list != null && list.size() > 0) {
            return false;
        }
        return true;
    }

    private String averageScore(List<Integer> scores) {
        if (scores != null && scores.size() > 0) {
            float score = 0;
            for (int i : scores) {
                score += i;
            }

            score = score / scores.size();
            return String.format("%.1f", score);
        }
        return "0.0";
    }

    private String sumScore(List<Integer> scores) {
        if (scores != null && scores.size() > 0) {
            int score = 0;
            for (int i : scores) {
                score += i;
            }

            return String.valueOf(score);
        }
        return "0";
    }


    private List<List<String>> transformReview(List<List<String>> list) {

        if (list != null) {
            List<List<String>> newList = new ArrayList<>();
            int m = list.size();
            int n = ((List) list.get(0)).size();

            String[][] array = new String[n][m];

            for (int i = 0; i < m; i++) {
                List row = (List) list.get(i);
                for (int j = 0; j < n; j++) {
                    String value = (String) row.get(j);
                    array[j][i] = value;
                }
            }

            for (int i = 0; i < n; i++) {
                List<String> newRow = new ArrayList<>();
                for (int j = 0; j < m; j++) {
                    newRow.add(array[i][j]);
                }
                newList.add(newRow);
            }

            return newList;
        }
        else {
            return null;
        }
    }
}
