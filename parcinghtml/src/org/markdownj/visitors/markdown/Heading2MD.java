package org.markdownj.visitors.markdown;

import org.jsoup.nodes.Node;
import org.markdownj.visitors.Visitor;

public class Heading2MD implements Visitor {
    @Override
    public String head(Node var1, int var2) {
        return "##";
    }

    @Override
    public String tail(Node var1, int var2) {
        return "";
    }

    @Override
    public String getTag() {
        return "h2";
    }
}
