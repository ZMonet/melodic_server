package com.akatsuki.util;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.List;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;
/**
 * Created by yusee on 2018/4/16.
 */
public class VerificationMail {

    /*sender*/
    private String sendEmailAccount = "2971024225@qq.com";

    private final String sendEmailPassword = "klvapzndpfktdcch";

    private final String sendEmailSMTPHost = "smtp.qq.com";

    public String getSendEmailAccount() {
        return sendEmailAccount;
    }

    public String getSendEmailPassword() {
        return sendEmailPassword;
    }

    public String getSendEmailSMTPHost() {
        return sendEmailSMTPHost;
    }

    private String verification;

    private String mail_message;

    private String type;

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMail_message() {
        return mail_message;
    }

    public void setMail_message(String mail_message) {
        this.mail_message = mail_message;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    /*receiver*/
    private String receiveEmailAccount = "";

    public String getReceiveEmailAccount() {
        return receiveEmailAccount;
    }

    public void setReceiveEmailAccount(String receiveEmailAccount) {
        this.receiveEmailAccount = receiveEmailAccount;
    }

    /**
     * 方法描述：递归遍历子节点，根据属性名和属性值，找到对应属性名和属性值的那个子孙节点。
     * @param node 要进行子节点遍历的节点
     * @param attrName 属性名
     * @param attrValue 属性值
     * @return 返回对应的节点或null
     */
    public Element getNodes(Element node, String attrName, String attrValue) {

        final List<Attribute> listAttr = node.attributes();
        for (final Attribute attr : listAttr) {
            final String name = attr.getName();
            final String value = attr.getValue();
            if(attrName.equals(name) && attrValue.equals(value)){
                return node;
            }
        }

        final List<Element> listElement = node.elements();
        for (Element e : listElement) {
            Element temp = getNodes(e,attrName,attrValue);
            if(temp != null){
                return temp;
            };
        }

        return null;
    }

    private Message createMessage(Session session,String title,String content) throws Exception{
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(getSendEmailAccount()));
        message.setRecipient(
                MimeMessage.RecipientType.TO,
                new InternetAddress(getReceiveEmailAccount())
        );

        /*title*/
        message.setSubject(title);
        /*content*/
        message.setContent(content,"text/html;charset=utf-8");
        message.setSentDate(new Date());

        message.saveChanges();
        return message;
    }

    public void sendVerificationMail() throws Exception{

        SAXReader reader = new SAXReader();
        Document document = null;

        /*argument setting*/
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", getSendEmailSMTPHost());
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");

        /*session setting*/
        Session session = Session.getDefaultInstance(props);
        /*session.setDebug(true);   // debug model*/

        /*document = reader.read(ResourceUtils.getFile("classpath:templates/mail-template.html"));*/
        document = reader.read(new ClassPathResource("templates/mail-template.html").getInputStream());
        Element root = document.getRootElement();

        Element verificationCode = getNodes(root,"id","verification_code");
        Element userEmail = getNodes(root,"id","user_email");
        Element mailMessage = getNodes(root,"id","mail_message");
        Element verificationContainer = getNodes(root,"id","verification_container");
        if(getType().equals("url")){
            Attribute style = verificationContainer.attribute("style");
            style.setValue("width: 200px;\n" +
                    "    height: 80px;\n" +
                    "    background-image: linear-gradient(120deg, #f6d365 0%, #fda085 100%);\n" +
                    "    line-height: 80px;\n" +
                    "    color: white;\n" +
                    "    border-radius: 5px;\n" +
                    "    box-shadow: 2px 0 25px 2px #f5e8bf;"+
                    "    display:flex");
            List list = new ArrayList();
            list.add(style);
            verificationContainer.setAttributes(list);
        }

        verificationCode.setText(getVerification());
        userEmail.setText(getReceiveEmailAccount().substring(0,4)+"***@qq.com");
        mailMessage.setText(getMail_message());

        String result = document.asXML();
        Message message = createMessage(session,getTitle(),result);

        Transport transport = session.getTransport();
        transport.connect(getSendEmailAccount(), getSendEmailPassword());
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    public VerificationMail(String receiveEmailAccount,String type,String title,String verification,String mail_message){
        setReceiveEmailAccount(receiveEmailAccount);
        setVerification(verification);
        setMail_message(mail_message);
        setType(type);
        setTitle(title);
    }

}
