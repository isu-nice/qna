package qna.helper.email;

public class TemplateEmailSendable implements EmailSendable{

    @Override
    public void send(String message) {
        System.out.println("send something");
    }
}
