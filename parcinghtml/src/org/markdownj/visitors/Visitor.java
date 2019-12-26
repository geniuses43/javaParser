package org.markdownj.visitors;

import org.jsoup.nodes.Node;

public interface Visitor {
    String head(Node var1, int var2);
    String tail(Node var1, int var2);
    String getTag(); // set to * to mean any tag; set to #text to mean text
}

