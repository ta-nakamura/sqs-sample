package sqs;

import java.util.ResourceBundle;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.eclipsesource.json.JsonObject;

import aws.AwsUtil;

public class Sample {

    /**
     * SQS登録サンプル処理
     * @param args
     */
    public static void main(String args[]) {


        try {
            // メッセージ内容作成
            JsonObject json = new JsonObject();
            json.add("type", "SqsTest");
            json.add("name", "テスト用メッセージ");
            SendMessageRequest request = new SendMessageRequest();
            request.setMessageBody(json.toString());

            // プロパティファイル読み込み
            ResourceBundle rb = ResourceBundle.getBundle("AwsCredentials");

            // SQSに登録
            AmazonSQSAsync sqs =  AwsUtil.getClient();
            request.setQueueUrl(rb.getString("url"));
            sqs.sendMessage(request);
            System.out.println("登録内容 :" + request.getMessageBody());

        } catch(Exception e) {
            System.out.println("e = " + e);
        }

    }
}
