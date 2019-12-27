package org.markdownj.visitors.bbcode;

import org.jsoup.nodes.Node;
import org.markdownj.visitors.Visitor;

public class ImgBB implements Visitor {
    @Override
    public String head(Node node, int i) {
        return "[img alt=\"" +
                node.attr("alt") +
                "\"]" +
                node.attr("src") +
                "[/img]";
    }

    @Override
    public String tail(Node node, int i) {
        return "";
    }

    @Override
    public String getTag() {
        return "img";
    }
}
