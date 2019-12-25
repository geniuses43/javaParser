package org.markdownj;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeVisitor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

enum OutputType {
    BBCode,
    MarkDown,
    Txt,
}

enum ListType {
    OrderedList,
    UnOrderedList,
}

class parcinghtml {

    public static void main(String[] args) throws IOException {
        OutputType outputType = chooseOutput();
        System.out.println(outputType);
        System.out.println("Введите ссылку");
        Scanner in = new Scanner(System.in);
        String path = in.next();
        get_txt(path);
        Document page = Jsoup.parse(new java.net.URL(path), 10000);
//        Document page = Jsoup.parse("\n" +
//                "<!DOCTYPE html>\n" +
//                "<html lang=\"en\">\n" +
//                "    <head>\n" +
//                "        <meta charset=\"utf-8\" />\n" +
//                "        <title>Hello World!</title>\n" +
//                "    </head>\n" +
//                "    <body>\n" +
//                "<b>BOLD<i>Itallic-BOLD</i></b>\n" +
//                "    </body>\n" +
//                "</html>");
        Visitor nodeVisitor = new Visitor(outputType);
        page.body().traverse(nodeVisitor);
        System.out.println(nodeVisitor.getResult());
//        StringBuilder result = new StringBuilder();
//        Elements els =page.body().children();
//        for (Element e :
//                els) {
//            // start block
//            result.append(start(e));
//
//            for (Node childNode :
//                    e.childNodes()) {
//                if (childNode instanceof TextNode){
//                    TextNode text = (TextNode)childNode;
//                }
//            }
//            //
//        }

//        System.out.println(HTML2MK.parse(page));
//        System.out.println("------------------------------------------------");
//        System.out.println(page.outerHtml());
    }

    private static OutputType chooseOutput() {
        System.out.println("Выберите формат вывода:\n1)Txt\n2)Markdown\n3)BBCode\n");
        Scanner in = new Scanner(System.in);
        do {
            int choose = in.nextInt();
            switch (choose) {
                case 1: {
                    return OutputType.Txt;
                }
                case 2: {
                    return OutputType.MarkDown;
                }
                case 3: {
                    return OutputType.BBCode;
                }
                default: {
                    System.out.println("Неизвестный ввод. Выберите число от 1 до 3\n");
                }
            }
        } while (true);
    }


    public static void get_txt(String path) {
        int count;
        byte[] buff = new byte[64];
        InputStream is = null;
        OutputStream out = null;

        try {
            is = new URL("https://" + path).openStream();
            out = new FileOutputStream("file.html");

            while ((count = is.read(buff)) != -1) {
                out.write(buff, 0, count);
            }
        } catch (IOException e) {
        }
    }

}

class HTML2MK {

    public static String parse(Document page) {
        StringBuilder result = new StringBuilder();

        for (Element e :
                page.select("h1, h2, h3, i, b, blockquote, ul, ol, img, a, code, table, p")) {
            result.append(HTML2MK.parseElement(e)).append("\n");
        }

        return result.toString();
    }

    public static String parseElement(Element e) {
        switch (e.tagName().toLowerCase()) {
            case "h1": {
                return HTML2MK.parseH1(e);
            }
            case "h2": {
                return HTML2MK.parseH2(e);
            }

            case "h3": {
                return HTML2MK.parseH3(e);
            }

            case "i": {
                return HTML2MK.parseI(e);
            }

            case "b": {
                return HTML2MK.parseB(e);
            }

            case "p": {
                return HTML2MK.parseP(e);
            }

            case "blockquote": {
                return HTML2MK.parseBlockQuote(e);
            }

            case "ul": {
                return HTML2MK.parseUl(e);
            }

            case "ol": {
                return HTML2MK.parseOl(e);
            }

            case "img": {
                return HTML2MK.parseImg(e);
            }

            case "a": {
                return HTML2MK.parseA(e);
            }

            case "code": {
                return HTML2MK.parseCode(e);
            }

//            case "table": {
//                return HTML2MK.parseTable(e);
//            }

            default: {
                return "[comment]: <> (Unparsed tag: " + e + ")";
            }
        }
    }
//
//    private static String parseTable(Element e) {
//        StringBuilder table = new StringBuilder();
//        Element tbody = e.child(0);
//        boolean headerFilled = false;
//        for (Element row :
//                tbody.children()) {
//            Elements children = row.children();
//            for (int i = 0; i < children.size(); i++) {
//                Element cell = children.get(i);
//                table.append(cell.text());
//                if (i != children.size() - 1) table.append(" | ");
//            }
//            table.append("\n");
//            if (!headerFilled) {
//                table.append("---|".repeat(tbody.children().size()));
//                headerFilled = true;
//                table.append("\n");
//            }
//        }
//        return String.valueOf(table);
//    }

    public static String parseH1(Element e) {
        StringBuilder res = new StringBuilder();
        if (e.children().size() > 0) {
            res.append("# ");
            for (Element child :
                    e.children()) {
                res.append(parseElement(child));
            }
//            closing block
            return String.valueOf(res);
        } else {
            return "# " + e.text();
        }
    }

    public static String parseH2(Element e) {

        return "## " + e.text();
    }

    public static String parseH3(Element e) {
        return "### " + e.text();
    }

    public static String parseP(Element e) {
        return e.text();
    }

    public static String parseI(Element e) {
        return "_" + e.text() + "_";
    }

    public static String parseB(Element e) {
        return "**" + e.text() + "**";
    }

    public static String parseCode(Element e) {
        return "```\n" + e.text() + "\n```";
    }

    public static String parseBlockQuote(Element e) {
        return ">" + String.join("\n>", e.text().split("\n"));
    }

    public static String parseUl(Element e) {
        StringBuilder list = new StringBuilder();
        for (Element child :
                e.children()) {
            list.append("* ");
            list.append(child.text());
            list.append("\n");
        }
        return String.valueOf(list);
    }

    public static String parseOl(Element e) {
        StringBuilder list = new StringBuilder();
        int i = 1;
        for (Element child :
                e.children()) {
            list.append(i++).append(". ");
            list.append(child.text());
            list.append("\n");
        }
        return String.valueOf(list);
    }

    public static String parseImg(Element e) {
        return "![" + e.attr("alt") + "](" + e.attr("scr") + ")";
    }

    public static String parseA(Element e) {
        if (e.hasAttr("href")) {
            return "[" + e.attr("href") + "](" + e.attr("text") + ")";
        } else {
            return "";
        }
    }
}


class Visitor implements NodeVisitor {

    private OutputType outputType;
    private StringBuilder result = new StringBuilder();
    ListType currentListType;
    int CountInt = 1;

    public Visitor(OutputType outputType) {
        this.outputType = outputType;
    }

    private static String parseTable(Node node, int i) {
        StringBuilder table = new StringBuilder();
        Node tbody = node.childNode(0);
        boolean headerFilled = false;
        for (Node row :
                tbody.childNodes()) {
            List<Node> childNodes = row.childNodes();
            for (i = 0; i < childNodes.size(); i++) {
//                if (node instanceof Element){
//                    Element el = (Element) node;
                Node cell = childNodes.get(i);
                if (cell instanceof TextNode) {
                    table.append(((TextNode) cell).text());
                }
                if (i != childNodes.size() - 1) table.append(" | ");
//                }

            }
            table.append("\n");
            if (!headerFilled) {
                table.append("---|".repeat(tbody.childNodes().size()));
                headerFilled = true;
                table.append("\n");
            }
        }
        return String.valueOf(table);
    }

    String getResult() {
        return String.valueOf(result);
    }

    @Override
    public void head(Node node, int i) {
        if (outputType == OutputType.Txt) {
            if (node instanceof TextNode) {
                result.append(((TextNode) node).text().trim());
            }
        } else {
            if (node instanceof TextNode) {
                TextNode text = (TextNode) node;
                result.append(text);
            } else if (node instanceof Element) {
                switch (node.nodeName().toLowerCase()) {
                    case "code": {
                        if (outputType == OutputType.MarkDown)
                            result.append("\n```\n");
                        else if (outputType == OutputType.BBCode)
                            result.append("[code]\n");
                        break;
                    }

                    case "color": {
                        if (outputType == OutputType.BBCode)
                            result.append("[color=" + node.attr("color") + "]");
                        break;
                    }
                    case "ul": {
                        if (outputType == OutputType.MarkDown)
                            currentListType = ListType.UnOrderedList;
                        else if (outputType == OutputType.BBCode) {
                            currentListType = ListType.UnOrderedList;
                            result.append("[ul]\n");
                        }
                        break;
                    }
                    case "ol": {
                        CountInt = 1;
                        if (outputType == OutputType.MarkDown)
                            currentListType = ListType.OrderedList;
                        else if (outputType == OutputType.BBCode) {
                            currentListType = ListType.OrderedList;
                            result.append("[ol]\n");
                        }
                        break;
                    }
                    case "li": {
                        if (outputType == OutputType.MarkDown){
                            if (currentListType == ListType.UnOrderedList)
                                result.append("* ");
                            else if (currentListType == ListType.OrderedList)
                            {
                                result.append(CountInt++).append(". ");
                            }
                        }
                        else if (outputType == OutputType.BBCode) {
                                result.append("\t[li]");
                        }
                        break;
                    }

                    case "h1": {
                        if (outputType == OutputType.MarkDown)
                            result.append("#");
                        else if (outputType == OutputType.BBCode)
                            result.append("[size=27]");
                        break;
                    }
                    case "h2": {
                        if (outputType == OutputType.MarkDown)
                            result.append("##");
                        else if (outputType == OutputType.BBCode)
                            result.append("[size=25]");
                        break;
                    }
                    case "h3": {
                        if (outputType == OutputType.MarkDown)
                            result.append("###");
                        else if (outputType == OutputType.BBCode)
                            result.append("[size=23]");
                        break;
                    }

                    case "blockquote": {
                        if (outputType == OutputType.MarkDown)
                            result.append(">");
                        else if (outputType == OutputType.BBCode)
                            result.append("[blockquote]");
                        break;
                    }

                    case "b": {
                        if (outputType == OutputType.MarkDown)
                            result.append("**");
                        else if (outputType == OutputType.BBCode)
                            result.append("[b]");
                        break;
                    }
                    case "i": {
                        if (outputType == OutputType.MarkDown)
                            result.append("_");
                        else if (outputType == OutputType.BBCode)
                            result.append("[i]");
                        break;
                    }
                    case "u": {
                        if (outputType == OutputType.BBCode)
                            result.append("[u]");
                        break;
                    }
                    case "s": {
                        if (outputType == OutputType.BBCode)
                            result.append("[s]");
                        break;
                    }
                    case "img": {
                        if (outputType == OutputType.MarkDown)
                            result.append("![");
                        else if (outputType == OutputType.BBCode)
                            result.append("[img]");
                        break;
                    }
                    case "table": {
                        if (outputType == OutputType.MarkDown)
                            result.append("");
                        else if (outputType == OutputType.BBCode)
                            result.append("[img]");
                        break;
                    }
                    case "a": {
                        if (outputType == OutputType.MarkDown) {
                            if (node.hasAttr("href")) {
                                result.append("[");
                            } else {
                                result.append("");
                            }
                        } else if (outputType == OutputType.BBCode)
                            result.append("[URL=" + node.attr("href") + "]");
                        break;
                    }

                    default: {
                        result.append("");
                    }
                }
            }
        }
    }

    @Override
    public void tail(Node node, int i) {
        if (outputType == OutputType.Txt) {
            if (node instanceof TextNode) {
                result.append("\n");
            }
        } else {
            if (node instanceof Element) {
                switch (node.nodeName().toLowerCase()) {
                    case "code": {
                        if (outputType == OutputType.MarkDown)
                            result.append("\n```\n");
                        else if (outputType == OutputType.BBCode)
                            result.append("[/code]\n");
                        break;
                    }
                    case "color": {
                        if (outputType == OutputType.BBCode)
                            result.append(")[/color]");
                        break;
                    }
                    case "blockquote": {
                        if (outputType == OutputType.MarkDown)
                            result.append("\n\n");
                        else if (outputType == OutputType.BBCode)
                            result.append("[/blockquote]");
                        break;
                    }
                    case "h1": {
                        if (outputType == OutputType.MarkDown)
                            result.append("#\n");
                        else if (outputType == OutputType.BBCode)
                            result.append("[/size]\n");
                        break;
                    }
                    case "ul": {
                        if (outputType == OutputType.BBCode) {
                            currentListType = ListType.UnOrderedList;
                            result.append("[/ul]\n");
                        }
                        break;
                    }
                    case "ol": {
                        if (outputType == OutputType.BBCode) {
                            currentListType = ListType.OrderedList;
                            result.append("[/ol]\n");
                        }
                        break;
                    }
                    case "li": {
                        if (outputType == OutputType.BBCode) {
                                result.append("[/li]");
                        }
                        result.append("\n");
                        break;
                    }
                    case "h2": {
                        if (outputType == OutputType.MarkDown)
                            result.append("##\n");
                        else if (outputType == OutputType.BBCode)
                            result.append("[/size]\n");
                        break;
                    }
                    case "h3": {
                        if (outputType == OutputType.MarkDown)
                            result.append("###\n");
                        else if (outputType == OutputType.BBCode)
                            result.append("[/size]\n");
                        break;
                    }
                    case "b": {
                        if (outputType == OutputType.MarkDown)
                            result.append("**\n\n");
                        else if (outputType == OutputType.BBCode)
                            result.append("[/b]\n");
                        break;
                    }
                    case "i": {
                        if (outputType == OutputType.MarkDown)
                            result.append("_\n");
                        else if (outputType == OutputType.BBCode)
                            result.append("[/i]\n");
                        break;
                    }
                    case "u": {
                        if (outputType == OutputType.BBCode)
                            result.append("[/u]\n");
                        break;
                    }
                    case "s": {
                        if (outputType == OutputType.BBCode)
                            result.append("[/s]\n");
                        break;
                    }
                    case "img": {
                        if (outputType == OutputType.MarkDown)
                            result.append("](" + node.attr("scr") + ")\n");
                        else if (outputType == OutputType.BBCode)
                            result.append("[/img]\n");
                        break;
                    }

                    case "a": {
                        if (outputType == OutputType.MarkDown) {
                            if (node.hasAttr("href")) {
                                result.append("](" + node.attr("href") + ")\n");
                            } else {
                                result.append("");
                            }
                        } else if (outputType == OutputType.BBCode)
                            result.append("[/URL]\n");
                        break;
                    }
                }
            }
        }
    }
}