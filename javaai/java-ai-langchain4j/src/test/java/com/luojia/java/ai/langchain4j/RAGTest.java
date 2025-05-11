package com.luojia.java.ai.langchain4j;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.onnx.HuggingFaceTokenizer;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.List;

@SpringBootTest
public class RAGTest {
    
    @Test
    public void testReadDocument() {
        // 加载单个文件
        Document document = FileSystemDocumentLoader.loadDocument("D:\\file\\测试.txt");
        System.out.println(document.text());

        // 从一个目录中加载所有文档
        List<Document> documents = FileSystemDocumentLoader.loadDocuments("D:\\file");
        for (Document document1 : documents) {
            System.out.println("=====================");
            System.out.println(document1.metadata());
            System.out.println(document1.text());
        }

        // 从一个目录中加载所有的.txt文档
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:*.txt");
        List<Document> documents1 = FileSystemDocumentLoader.loadDocuments("D:\\file", pathMatcher, new TextDocumentParser());
        
        // 从一个目录及子目录中加载所有文档
        List<Document> documents2 = FileSystemDocumentLoader.loadDocumentsRecursively("D:\\file", new TextDocumentParser());
    }

    @Test
    public void testParsePDF() {
        Document document = FileSystemDocumentLoader.loadDocument("D:\\file\\测试.pdf", new ApachePdfBoxDocumentParser());
        System.out.println(document.metadata());
        System.out.println(document.text());
    }

    @Test
    public void testReadDocumentAndStore() {
        // 使用FileSystemDocumentLoader读取指定目录下的知识库文档
        // 并使用默认的文档解析器对文档进行解析(TextDocumentParser)
        Document document = FileSystemDocumentLoader.loadDocument("D:/file/微调大模型.md");
        //为了简单起见，我们暂时使用基于内存的向量存储
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        // ingest
        // 1、分割文档：默认使用递归分割器，将文档分割为多个文本片段，每个片段包含不超过300个token，并且有30个token的重叠部分保证连贯性
        // DocumentByParagraphSplitter(DocumentByLineSplitter(DocumentBySentenceSplitter(DocumentByWordSplitter)))
        // 2、文本向量化：使用一个LangChain4j内置的轻量化向量模型对每个文本片段进行向量化
        // 3、将原始文本和向量存储到向量数据库中(InMemoryEmbeddingStore)
        EmbeddingStoreIngestor.ingest(document, embeddingStore);
        // 查看向量数据库内容
        System.out.println(embeddingStore);
    }

    /**
     * 文档分割
     */
    @Test
    public void testDocumentSplitter() {
        // 使用FileSystemDocumentLoader读取指定目录下的知识库文档
        // 并使用默认的文档解析器对文档进行解析(TextDocumentParser)
        Document document = FileSystemDocumentLoader.loadDocument("E:/file/微调大模型.md");
        // 为了简单起见，我们暂时使用基于内存的向量存储
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        // 自定义文档分割器
        // 按段落分割文档：每个片段包含不超过300个token，并且有30个token的重叠部分保证连贯性
        DocumentByParagraphSplitter documentSplitter = new DocumentByParagraphSplitter(
                300,
                30,
                // token分词器：按token计算
                new HuggingFaceTokenizer());
        // 按字符计算
        // DocumentByParagraphSplitter documentSplitter = new DocumentByParagraphSplitter(30o, 30);
        EmbeddingStoreIngestor
                .builder()
                .embeddingStore(embeddingStore)
                .documentSplitter(documentSplitter)
                .build()
                .ingest(document);

    }

    @Test
    public void testDocumentSplitter2() {
        String text = "这是一个示例文本，用于测试 token 长度的计算。";
        UserMessage userMessage = UserMessage.userMessage(text);
        // 计算token长度 HuggingFaceTokenizer 进去可以看到实现了 Tokenizer 接口
        // 点进去 Tokenizer，使用Ctrl + H 可以看到它的不同实现类
        HuggingFaceTokenizer tokenizer = new HuggingFaceTokenizer();
        int count = tokenizer.estimateTokenCountInMessage(userMessage);
        // token长度: 20
        System.out.println("token长度: " + count);
    }
}
