package com.wit.xzy.community.Test;

import com.wit.xzy.community.util.SensitiveFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author ZongYou
 **/
@SpringBootTest
public class SensitiveFilterTest {

@Autowired
private SensitiveFilter sensitiveFilter;

    @Test
    void testsensitivewordFilter(){
        String text = "这里可以赌博,可以嫖娼,可以吸毒,可以开票,哈哈哈!";
        text = sensitiveFilter.filter(text);
        System.out.println(text);
    }
}
