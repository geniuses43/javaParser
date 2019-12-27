package org.markdownj.visitors.markdown;

import org.jsoup.nodes.Node;
import org.markdownj.visitors.Visitor;

public class LinksMD implements Visitor {
    @Override
    public String head(Node var1, int var2) {
        if (var1.hasAttr("href")){
            return "[";
        }
        else return "";
    }

    @Override
    public String tail(Node var1, int var2) {
        if (var1.hasAttr("href")){
            return "]("
                    + var1.attr("href")
                    + ")\n";
        }
        else return "";
    }

    @Override
    public String getTag() {
        return "a";
    }
}
