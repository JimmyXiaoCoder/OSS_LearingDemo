package com.bee.oss_learning_demo;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.Properties;

@SpringBootTest
class OssLearningDemoApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * 文件上传下载测试
     * @throws Exception
     */
    @Test
    public void ossTest()  throws Exception{

        String endpoint = "https://oss-cn-guangzhou.aliyuncs.com";
        // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
        CredentialsProvider credentialsProvider = getProvider();
        // 填写Bucket名称，例如examplebucket。Bucket名称在OSS范围内必须全局唯一。
        String bucketName = "jimmy-bucket-2024-7";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);

//        try {
//            // 创建存储空间。
//            ossClient.createBucket(bucketName);
//
//        } catch (OSSException oe) {
//            System.out.println("Caught an OSSException, which means your request made it to OSS, "
//                    + "but was rejected with an error response for some reason.");
//            System.out.println("Error Message:" + oe.getErrorMessage());
//            System.out.println("Error Code:" + oe.getErrorCode());
//            System.out.println("Request ID:" + oe.getRequestId());
//            System.out.println("Host ID:" + oe.getHostId());
//        } catch (ClientException ce) {
//            System.out.println("Caught an ClientException, which means the client encountered "
//                    + "a serious internal problem while trying to communicate with OSS, "
//                    + "such as not being able to access the network.");
//            System.out.println("Error Message:" + ce.getMessage());
//        } finally {
//            if (ossClient != null) {
//                ossClient.shutdown();
//            }
//        }

        String objectName = "D:\\Users\\10636\\Desktop\\code\\code.txt";
        File file = new File(objectName);

            //文件上传
//            ossClient.putObject(bucketName,objectName,file);

        //文件下载
        OSSObject object = ossClient.getObject(bucketName, objectName);
        InputStream objectContent = object.getObjectContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(objectContent));

        while(true){
            String s = reader.readLine();
            if(s==null || s.isEmpty()){
                break;
            }
            System.out.println(s);

        }
        objectContent.close();
        if (ossClient != null) {
            ossClient.shutdown();
        }

//        catch (OSSException oe) {
//            System.out.println("Caught an OSSException, which means your request made it to OSS, "
//                    + "but was rejected with an error response for some reason.");
//            System.out.println("Error Message:" + oe.getErrorMessage());
//            System.out.println("Error Code:" + oe.getErrorCode());
//            System.out.println("Request ID:" + oe.getRequestId());
//            System.out.println("Host ID:" + oe.getHostId());
//        } catch (ClientException ce) {
//            System.out.println("Caught an ClientException, which means the client encountered "
//                    + "a serious internal problem while trying to communicate with OSS, "
//                    + "such as not being able to access the network.");
//            System.out.println("Error Message:" + ce.getMessage());
//        } finally {
//            if (ossClient != null) {
//                ossClient.shutdown();
//            }
//        }
    }

    /**
     * 创建存储空间
     * @throws Exception
     */
    @Test
    public void createBucket() throws Exception{

        String endpoint = "https://oss-cn-guangzhou.aliyuncs.com";
        // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
        CredentialsProvider credentialsProvider = getProvider();
        // 填写Bucket名称，例如examplebucket。Bucket名称在OSS范围内必须全局唯一。
        String bucketName = "jimmy-bucket-2024-7-20";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);

        // 创建存储空间。
        ossClient.createBucket(bucketName);

        if (ossClient != null) {
            ossClient.shutdown();
        }

    }

    @Test
    public void getObjectList() throws Exception{
        String endpoint = "https://oss-cn-guangzhou.aliyuncs.com";
        // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
        CredentialsProvider credentialsProvider = getProvider();
        // 填写Bucket名称，例如examplebucket。Bucket名称在OSS范围内必须全局唯一。
        String bucketName = "jimmy-bucket-2024-7";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);

        ObjectListing objectListing = ossClient.listObjects(bucketName);
        for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            System.out.println("fileName:"+objectSummary.getKey());
            System.out.println("size:"+objectSummary.getSize());
        }
        if(ossClient!=null){
            ossClient.shutdown();
        }
    }



    private static CredentialsProvider getProvider() throws IOException {
        Properties properties = new Properties();
        // 设置config.ini文件路径
        String configFilePath = "src/main/resources/config.ini";

        // 读取配置文件
        FileInputStream input = new FileInputStream(configFilePath);
        properties.load(input);
        input.close();

        // 从配置文件中获取AK和SK
        String accessKeyId = properties.getProperty("accessKeyId");
        String accessKeySecret = properties.getProperty("accessKeySecret");

        return new DefaultCredentialProvider(accessKeyId, accessKeySecret);

    }

}
