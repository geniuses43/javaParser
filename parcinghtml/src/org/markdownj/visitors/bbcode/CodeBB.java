package org.markdownj.visitors.bbcode;

import org.jsoup.nodes.Node;
import org.markdownj.visitors.Visitor;

public class CodeBB implements Visitor {
    @Override
    public String head(Node node, int i) {
        return "[code]\n";
    }

    @Override
    public String tail(Node node, int i) {
        return "[/code]";
    }

    @Override
    public String getTag() {
        return "code";
    }
}
