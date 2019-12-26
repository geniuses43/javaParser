package org.markdownj;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeVisitor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

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
        Document page = Jsoup.parse(new java.net.URL(path), 10000);

        Visitor nodeVisitor = new Visitor(outputType);
        page.body().traverse(nodeVisitor);
        String result = nodeVisitor.getResult();
        System.out.println(result);

        if (outputType == OutputType.Txt) {
            try (FileWriter writer = new FileWriter("PAGE.txt")) {
                writer.append(result);
            }
        } else if (outputType == OutputType.MarkDown) {
            try (FileWriter writer = new FileWriter("PAGE.md")) {
                writer.append(result);
            }
        } else if (outputType == OutputType.BBCode) {
            try (FileWriter writer = new FileWriter("PAGE.bbc")) {
                writer.append(result);
            }
        }
    }

    private static OutputType chooseOutput() {
        System.out.println("Выберите формат вывода:\n1)Txt\n2)Markdown\n3)BBCode");
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
}

class Visitor implements NodeVisitor {

    private ListType currentListType;
    private Stack<Integer> listCounters = new Stack<>(); // Стак, отвечающий за вложенность списков.
    private OutputType outputType;
    private StringBuilder result = new StringBuilder();

    public Visitor(OutputType outputType) {
        this.outputType = outputType;
    }

    private int incrementTheMostNestedCounter() {
        Integer counter = listCounters.pop();
        listCounters.push(counter + 1);
        return counter + 1;
    }


    String getResult() {
        return String.valueOf(result);
    }

    private String trim(TextNode t) {
        String s = t.getWholeText();
//        if (s.equals("\n\n")){
        if (s.endsWith("\n\n")) {
            return "\n";
        } else {
            return s.trim();
        }
    }

    @Override
    public void head(Node node, int i) {
        if (outputType == OutputType.Txt) {
            if (node instanceof TextNode) {
                TextNode text = (TextNode) node;
                result.append(trim(text));
            }
        } else {
            if (node instanceof TextNode) {
                TextNode text = (TextNode) node;
                result.append(trim(text));
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
                            result.append("[color=").append(node.attr("color")).append("]");
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
//                        CountInt = 1;
                        listCounters.push(0);
                        if (outputType == OutputType.MarkDown)
                            currentListType = ListType.OrderedList;
                        else if (outputType == OutputType.BBCode) {
                            currentListType = ListType.OrderedList;
                            result.append("[ol]\n");
                        }
                        break;
                    }
                    case "li": {
                        if (outputType == OutputType.MarkDown) {
                            if (currentListType == ListType.UnOrderedList)
                                result.append("* ");
                            else if (currentListType == ListType.OrderedList) {
                                result.append("\t".repeat(listCounters.size() - 1))
                                        .append(incrementTheMostNestedCounter())
                                        .append(". ");
//                                result.append(CountInt++).append(". ");
                            }
                        } else if (outputType == OutputType.BBCode) {
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
                            result.append("[quote]");
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
                        else if (outputType == OutputType.MarkDown)
                            result.append("~~");
                        break;
                    }
                    case "img": {
                        if (outputType == OutputType.MarkDown)
                            result.append("![");
                        else if (outputType == OutputType.BBCode)
                            result.append("[img alt=\"");
                        break;
                    }
                    case "a": {
                        if (outputType == OutputType.MarkDown) {
                            if (node.hasAttr("href")) {
                                result.append("[");
                            }
                        } else if (outputType == OutputType.BBCode)
                            result.append("[URL=").append(node.attr("href")).append("]");
                        break;
                    }
                    case "br": {
                        result.append("\n");
                        break;
                    }

                    default: {
                    }
                }
            }
        }
    }

    @Override
    public void tail(Node node, int i) {
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
                        result.append("[/quote]");
                    break;
                }
                case "h1":
                case "h2":
                case "h3": {
                    if (outputType == OutputType.BBCode)
                        result.append("[/size]\n");
                    break;
                }
                case "ul": {
                    if (outputType == OutputType.BBCode) {
                        currentListType = ListType.UnOrderedList;
                        result.append("[/ul]");
                    }
                    break;
                }
                case "ol": {
                    listCounters.pop();
                    if (outputType == OutputType.BBCode) {
                        currentListType = ListType.OrderedList;
                        result.append("[/ol]");
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
                case "b": {
                    if (outputType == OutputType.MarkDown)
                        result.append("**");
                    else if (outputType == OutputType.BBCode)
                        result.append("[/b]");
                    break;
                }
                case "i": {
                    if (outputType == OutputType.MarkDown)
                        result.append("_");
                    else if (outputType == OutputType.BBCode)
                        result.append("[/i]");
                    break;
                }
                case "u": {
                    if (outputType == OutputType.BBCode)
                        result.append("[/u]");
                    break;
                }
                case "s": {
                    if (outputType == OutputType.BBCode)
                        result.append("[/s]");
                    else if (outputType == OutputType.MarkDown)
                        result.append("~~");
                    break;
                }
                case "img": {
                    if (outputType == OutputType.MarkDown)
                        result.append(node.attr("alt"))
                                .append("](")
                                .append(node.attr("src"))
                                .append(")");
                    else if (outputType == OutputType.BBCode)
                        result.append(node.attr("alt"))
                                .append("\"]")
                                .append(node.attr("src"))
                                .append("[/img]");
                    break;
                }

                case "a": {
                    if (outputType == OutputType.MarkDown) {
                        if (node.hasAttr("href")) {
                            result.append("](").append(node.attr("href")).append(")\n");
                        }
                    } else if (outputType == OutputType.BBCode)
                        result.append("[/URL]\n");
                    break;
                }
            }
        }
    }
}