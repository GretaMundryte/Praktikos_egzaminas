import lt.vtmc.exam.Bus;
import lt.vtmc.exam.TransportManager;
import lt.vtmc.exam.test.BaseTest;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class GretaMundryte_examTest extends BaseTest {

    @Override
    protected TransportManager createTransportManager() {
        TransportManager transportManager = new TransportManagerImpl();
        return transportManager;
    }
}