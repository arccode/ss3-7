import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * CryptoTest : 编码解码测试
 *
 * @author http://arccode.net
 * @since 2015-03-15 23:32
 */
public class CryptoTest {

    private Logger logger = LoggerFactory.getLogger(CryptoTest.class);

    @Test
    public void md5() {

        Md5PasswordEncoder encoder = new Md5PasswordEncoder();

        logger.info(encoder.encodePassword("user", ""));
        Assert.assertEquals(encoder.encodePassword("user",""), "ee11cbb19052e40b07aac0ca060c23ee");

    }
}
