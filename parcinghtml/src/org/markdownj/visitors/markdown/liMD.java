package org.markdownj.visitors.markdown;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.markdownj.visitors.Visitor;

public class liMD implements Visitor {
    @Override
    public String head(Node node, int i) {
        Element parent = (node.hasParent() && node.parent() instanceof Element) ?
                (Element)node.parent() : null;
        Element tag = (node instanceof Element) ? (Element)node : null;
        if (parent != null) {
            StringBuilder result = new StringBuilder();
            result.append("\t".repeat(i-2));
            // i stands for node depth of <li>. So i-1 is the depth of the list,
            // thus we should place  max(0, i-2) "\t"
            if (parent.tagName().equals("ul")){
                result.append("* ");
            }else if (parent.tagName().equals("ol")){
                assert tag != null;
                result.append(tag.elementSiblingIndex() + 1).append(". ");
            }
            return String.valueOf(result);
        }
        return "";
    }

    @Override
    public String tail(Node node, int i) {
        return "\n";
    }

    @Override
    public String getTag() {
        return "li";
    }
}
