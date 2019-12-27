package org.markdownj.visitors.bbcode;

import org.jsoup.nodes.Node;
import org.markdownj.visitors.Visitor;

public class Heading1BB  implements Visitor {
    @Override
    public String head(Node node, int i) {
        return "[size=27]";
    }

    @Override
    public String tail(Node node, int i) {
        return "[/size]";
    }

    @Override
    public String getTag() {
        return "h1";
    }
}
