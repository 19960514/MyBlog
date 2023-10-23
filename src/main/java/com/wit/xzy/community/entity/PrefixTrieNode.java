package com.wit.xzy.community.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ZongYou
 **/
public class PrefixTrieNode {

    // 关键词结束标识
    private boolean isKeywordEnd = false;

    // 子节点(key是下级字符,value是下级节点)
    private Map<Character, PrefixTrieNode> subNodes = new HashMap<>();

    public boolean isKeywordEnd() {
        return isKeywordEnd;
    }

    public void setKeywordEnd(boolean keywordEnd) {
        isKeywordEnd = keywordEnd;
    }

    // 添加子节点
    public void addSubNode(Character c, PrefixTrieNode node) {
        subNodes.put(c, node);
    }

    // 获取子节点
    public PrefixTrieNode getSubNode(Character c) {
        return subNodes.get(c);
    }

}

