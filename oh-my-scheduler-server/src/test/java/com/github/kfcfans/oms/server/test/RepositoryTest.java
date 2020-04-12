package com.github.kfcfans.oms.server.test;

import com.github.kfcfans.common.utils.NetUtils;
import com.github.kfcfans.oms.server.common.constans.JobStatus;
import com.github.kfcfans.common.TimeExpressionType;
import com.github.kfcfans.oms.server.persistence.model.InstanceLogDO;
import com.github.kfcfans.oms.server.persistence.model.JobInfoDO;
import com.github.kfcfans.oms.server.persistence.model.OmsLockDO;
import com.github.kfcfans.oms.server.persistence.repository.InstanceLogRepository;
import com.github.kfcfans.oms.server.persistence.repository.JobInfoRepository;
import com.github.kfcfans.oms.server.persistence.repository.OmsLockRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据库层测试
 *
 * @author tjq
 * @since 2020/4/5
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepositoryTest {

    @Resource
    private JobInfoRepository jobInfoRepository;
    @Resource
    private OmsLockRepository omsLockRepository;
    @Resource
    private InstanceLogRepository instanceLogRepository;

    /**
     * 需要证明批量写入失败后会回滚
     */
    @Test
    public void testBatchLock() {

        List<OmsLockDO> locks = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            OmsLockDO lockDO = new OmsLockDO("lock" + i, NetUtils.getLocalHost());
            locks.add(lockDO);
        }
        omsLockRepository.saveAll(locks);
        omsLockRepository.flush();
    }

    @Test
    public void testSelectCronJobSQL() {
        List<JobInfoDO> result = jobInfoRepository.findByAppIdInAndStatusAndTimeExpressionTypeAndNextTriggerTimeLessThanEqual(Lists.newArrayList(1L), JobStatus.ENABLE.getV(), TimeExpressionType.CRON.getV(), System.currentTimeMillis());
        System.out.println(result);
    }

    @Test
    public void testUpdate() {
        InstanceLogDO updateEntity = new InstanceLogDO();
        updateEntity.setId(22L);
        updateEntity.setActualTriggerTime(System.currentTimeMillis());
        updateEntity.setResult("hahaha");
        instanceLogRepository.saveAndFlush(updateEntity);
    }

    @Test
    public void testExecuteLogUpdate() {
        instanceLogRepository.update4Trigger(1586310414570L, 2, 100, System.currentTimeMillis(), "192.168.1.1", "NULL");
        instanceLogRepository.update4FrequentJob(1586310419650L, 2, 200);
    }

}
