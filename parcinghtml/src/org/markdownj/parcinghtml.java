package org.markdownj;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeVisitor;
import org.markdownj.visitors.Visitor;
import org.markdownj.visitors.bbcode.*;
import org.markdownj.visitors.markdown.*;
import org.markdownj.visitors.txt.TXT;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

enum OutputType {
    BBCode,
    MarkDown,
    Txt,
}


class parcinghtml {

    public static void main(String[] args) throws IOException {
        OutputType outputType = chooseOutput();
//        OutputType outputType = OutputType.MarkDown;
        System.out.println(outputType);
//        System.out.println("Введите ссылку");
        Scanner in = new Scanner(System.in);
//        String path = in.next();
        String path = "https://geniuses43.github.io/";
        Document page = Jsoup.parse(new java.net.URL(path), 10000);

        TranslationalVisitor nodeVisitor = new TranslationalVisitor(outputType);
        nodeVisitor.addVisitors(OutputType.Txt,
                new TXT());
        nodeVisitor.addVisitors(OutputType.MarkDown,
                new BoldMD(), new ItallicMD(),
                new BlockquoteMD(), new BreaksMD(),
                new CodeMD(), new Heading1MD(),
                new Heading2MD(), new Heading3MD(),
                new ImgMD(), new LinksMD(),
                new OlMD(), new StrikeMD(),
                new UlMD(), new liMD());
        nodeVisitor.addVisitors(OutputType.BBCode,
                new BoldBB(), new BlockquoteBB(),
                new CodeBB(), new ColorBB(),
                new Heading1BB(), new Heading2BB(),
                new Heading3BB(), new ImgBB(),
                new ItallicBB(), new liBB(),
                new LineBreakBB(), new LinkBB(),
                new OlBB(), new StrikeBB(),
                new UlBB(), new UnderlineBB());
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
        String errorMsg = "Неизвестный ввод. Выберите число от 1 до 3\n";
        Scanner in = new Scanner(System.in);
        do {
            try {
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
                        System.out.println(errorMsg);
                    }
                }
            } catch (InputMismatchException ignored) {
                System.out.println(errorMsg);
                in.nextLine();
            }
        } while (true);
    }
}

class TranslationalVisitor implements NodeVisitor {

    private HashMap<OutputType, HashMap<String, org.markdownj.visitors.Visitor>> visitors = new HashMap<>();

    private OutputType outputType;
    private StringBuilder result = new StringBuilder();

    public TranslationalVisitor(OutputType outputType) {
        this.outputType = outputType;
    }

    public void setOutputType(OutputType outputType) {
        this.outputType = outputType;
    }

    public void addVisitors(OutputType type, org.markdownj.visitors.Visitor... visitors) {
        HashMap<String, org.markdownj.visitors.Visitor> typeMap = this.visitors.getOrDefault(type, new HashMap<>());
        for (org.markdownj.visitors.Visitor visitor : visitors
        ) {
            typeMap.put(visitor.getTag(), visitor);
        }
        this.visitors.put(type, typeMap);
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

    private Visitor getVisitor(Node node, int i) {
        HashMap<String, Visitor> translators = this.visitors.getOrDefault(this.outputType, null);
        Visitor defaultTranslator = translators.getOrDefault("*", null);
        Visitor translator;
        if (defaultTranslator != null) {
            translator = defaultTranslator;
        } else {
            translator = translators.getOrDefault(node.nodeName().toLowerCase(), null);
        }
        return translator;
    }

    @Override
    public void head(Node node, int i) {

        if (node instanceof TextNode) {
            TextNode text = (TextNode) node;
            result.append(trim(text));
            return;
        }

        Visitor translator = this.getVisitor(node, i);
        if (translator != null) { //expected tag
            result.append(translator.head(node, i));
        }
    }

    @Override
    public void tail(Node node, int i) {

        Visitor translator = this.getVisitor(node, i);
        if (translator != null) { //expected tag
            result.append(translator.tail(node, i));
        }
    }
}