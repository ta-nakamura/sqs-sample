package aws;

import com.amazonaws.auth.*;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.buffered.AmazonSQSBufferedAsyncClient;
import com.amazonaws.services.sqs.buffered.QueueBufferConfig;

public class AwsUtil {

    private static AmazonSQSAsync client;

    private static final Regions region = Regions.AP_NORTHEAST_1;

    private static final Object SQS_LOCK = new Object();

    /**
     * SQSクライアントの作成と取得
     * @return AmazonSQSAsync
     */
    public static AmazonSQSAsync getClient() {
        if (client == null) {
            synchronized (SQS_LOCK) {
                // 最大待ち時間等設定があれば指定する
                QueueBufferConfig config = new QueueBufferConfig();

                // SQSクライアントの作成
                client = new AmazonSQSBufferedAsyncClient(
                        AmazonSQSAsyncClientBuilder.standard()
                            .withCredentials(getCredentialsProvider())
                            .withRegion(region)
                            .build(),
                        config
                );
            }
        }
        return client;
    }

    /**
     * 認証情報をプロパティファイルから取得しセット
     * @return AWSCredentialsProvider
     */
    private static AWSCredentialsProvider getCredentialsProvider() {
        return new AWSCredentialsProviderChain(new ClasspathPropertiesFileCredentialsProvider());
    }
}
