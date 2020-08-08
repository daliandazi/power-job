import com.github.kfcfans.powerjob.common.PowerJobDKey;
import com.github.kfcfans.powerjob.common.utils.NetUtils;
import org.junit.jupiter.api.Test;

/**
 * NetUtilsTest
 *
 * @author tjq
 * @since 2020/8/8
 */
public class NetUtilsTest {

    @Test
    public void testOrigin() {
        System.out.println(NetUtils.getLocalHost());
    }

    @Test
    public void testPreferredNetworkInterface() {
        System.setProperty(PowerJobDKey.PREFERRED_NETWORK_INTERFACE, "en5");
        System.out.println(NetUtils.getLocalHost());
    }

}
