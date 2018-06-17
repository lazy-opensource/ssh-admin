
<%@page import="java.awt.geom.AffineTransform"%>
<%@ page language="java" import="java.util.*" pageEncoding="GB18030" contentType="image/jpeg" import="java.awt.*,
java.awt.image.*,java.util.*,javax.imageio.*,java.io.*" %>


<%!

/*
�����������
*/
//��ѡ�ַ��б�
char[] chars = "2345678ABCDEFGHJKLMPQRSTUVWXYabcdefhkmnqrstuvwx"
.toCharArray();
//��ѡ����
String[] fontNames = new String[] { "Courier", "Arial",
        "Verdana", "Georgia", "Times", "Tahoma" };
//��ѡ����
int[] fontStyle = new int[] { Font.PLAIN, Font.BOLD, Font.ITALIC, Font.BOLD | Font.ITALIC };
//���ߡ������ַ�����������������
int width = 100;
int height = 30;
int charCnt = 4;
int disturbLineNum = 10;

//����Ƕ�
private double getRandomArch() {
    return ((int) (Math.random() * 1000) % 2 == 0 ? -1 : 1) * Math.random();
}
//�����ɫ
private Color getRandomColor() {
    int r = (int) (Math.random() * 10000) % 200;
    int g = (int) (Math.random() * 10000) % 200;
    int b = (int) (Math.random() * 10000) % 200;
    return new Color(r, g, b);
}
//�������
private String getRandomFontName() {
    int pos = (int) (Math.random() * 10000) % (fontNames.length);
    return fontNames[pos];
}
//�������
private int getRandomStyle() {
    int pos = (int) (Math.random() * 10000) % (fontStyle.length);
    return fontStyle[pos];
}
//��������С
private int getRandomSize() {
    int max = (int) (this.height * 0.9);
    int min = (int) (this.height * 0.6);
    return (int) (Math.random() * 10000) % (max - min + 1) + min;
}
//����ַ�
private char[] generateCode() {
    char[] ret = new char[charCnt];
    for (int i = 0; i < charCnt; i++) {
        int letterPos = (int) (Math.random() * 10000) % (chars.length);
        ret[i] = chars[letterPos];
    }
    return ret;
}
//�����ַ�ͼƬ
private BufferedImage generateBuffImg(char c) {
    String tmp = Character.toString(c);
    Color forecolor = getRandomColor();
    Color backcolor = new Color(255, 255, 255, 0);
    String fontName = getRandomFontName();
    int fontStyle = getRandomStyle();
    int fontSize = getRandomSize();
    int strX = (this.height - fontSize) / 2;
    int strY = (this.height - fontSize) / 2 + fontSize;
    double arch = getRandomArch();

    BufferedImage ret = new BufferedImage(this.height, this.height,
            BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = ret.createGraphics();
    g.setColor(backcolor);
    g.fillRect(0, 0, this.height, this.height);

    g.setColor(forecolor);
    g.setFont(new Font(fontName, fontStyle, fontSize));
    g.rotate(arch, this.height / 2, this.height / 2);
    g.drawString(tmp, strX, strY);

    g.dispose();
    return ret;
}
%>

<%

//����ҳ�治����
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);


/*
* ������֤��ͼƬ
*/
BufferedImage bi = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
Graphics2D g = bi.createGraphics();
g.setColor(new Color(245, 245, 245));
g.fillRect(0, 0, width, height);

/*
* ���Ƹ�����
*/
for (int i = 0; i < disturbLineNum; i++) {
    g.setColor(getRandomColor());
    int x = (int) (Math.random() * 10000) % (width + 1) + 1;
    int x1 = (int) (Math.random() * 10000) % (width + 1) + 1;
    int y = (int) (Math.random() * 10000) % (height + 1) + 1;
    int y1 = (int) (Math.random() * 10000) % (height + 1) + 1;
    g.drawLine(x, y, x1, y1);
}


//�������ɵ��ַ�
BufferedImage[] bis = new BufferedImage[charCnt];
char[] codes = generateCode();
for (int i = 0; i < charCnt; i++) {
    bis[i] = generateBuffImg(codes[i]);
    g.drawImage(bis[i], null, (int) (this.height * 0.7) * i, 0);
}

//����֤�����SESSION
session.setAttribute("rand",new String(codes));

//ͼ����Ч
g.dispose();

//���ͼ��ҳ��
ImageIO.write(bi, "gif", response.getOutputStream());

//weblogic ����Ҫע��, �������IO�쳣: java.io.IOException: response already committed  
//�������������out����
out.clear();
out = pageContext.pushBody();

%>


