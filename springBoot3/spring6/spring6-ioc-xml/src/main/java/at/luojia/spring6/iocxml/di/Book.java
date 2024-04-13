package at.luojia.spring6.iocxml.di;

public class Book {

    private String bname;
    private String author;

    public Book() {
        System.out.println("无参构造器执行了");
    }

    public Book(String bname, String author) {
        this.bname = bname;
        this.author = author;
        System.out.println("有参构造器执行了");
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bname='" + bname + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    public static void main(String[] args) {
        // set 方法注入
        Book book = new Book();
        book.setBname("图书-依赖注入");
        book.setAuthor("Romantic-Lei");

        // 构造器注入
        Book book1 = new Book("java", "lei");

    }
}
