package org.markdownj.visitors.markdown;

import org.jsoup.nodes.Node;
import org.markdownj.visitors.Visitor;

public class ItallicMD implements Visitor {
    @Override
    public String head(Node var1, int var2) {
        return "_";
    }

    @Override
    public String tail(Node var1, int var2) {
        return "_";
    }

    @Override
    public String getTag() {
        return "i";
    }
}
