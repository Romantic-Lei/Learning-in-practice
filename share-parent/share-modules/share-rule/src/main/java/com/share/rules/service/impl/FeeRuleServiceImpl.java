package com.share.rules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.share.rule.domain.FeeRule;
import com.share.rule.domain.FeeRuleRequestForm;
import com.share.rule.domain.FeeRuleResponseVo;
import com.share.rules.domain.vo.FeeRuleRequest;
import com.share.rules.domain.vo.FeeRuleResponse;
import com.share.rules.mapper.FeeRuleMapper;
import com.share.rules.service.IFeeRuleService;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


/**
 * 费用规则Service业务层处理
 *
 * @author atguigu
 * @date 2024-10-25
 */
@Slf4j
@Service
public class FeeRuleServiceImpl extends ServiceImpl<FeeRuleMapper, FeeRule> implements IFeeRuleService
{
    @Autowired
    private FeeRuleMapper feeRuleMapper;

    @Autowired
    private KieContainer kieContainer;

    //计算订单费用
    @Override
    public FeeRuleResponseVo calculateOrderFee(FeeRuleRequestForm feeRuleRequestForm) {
        //1 开启会话
        KieSession kieSession = kieContainer.newKieSession();

        //2 创建传入数据对象 设置数据
        FeeRuleRequest feeRuleRequest = new FeeRuleRequest();
        feeRuleRequest.setDurations(feeRuleRequestForm.getDuration());

        //3 创建返回数据对象
        FeeRuleResponse feeRuleResponse = new FeeRuleResponse();
        kieSession.setGlobal("feeRuleResponse", feeRuleResponse);

        //4 对象传入到会话对象里面
        kieSession.insert(feeRuleRequest);

        //5 触发规则
        kieSession.fireAllRules();

        //6 中止会话
        kieSession.dispose();

        //7 封装返回需要数据
        FeeRuleResponseVo feeRuleResponseVo = new FeeRuleResponseVo();
        feeRuleResponseVo.setTotalAmount(new BigDecimal(feeRuleResponse.getTotalAmount()));
        feeRuleResponseVo.setFreePrice(new BigDecimal(feeRuleResponse.getFreePrice()));
        feeRuleResponseVo.setFreeDescription(feeRuleResponse.getFreeDescription());
        feeRuleResponseVo.setExceedDescription(feeRuleResponse.getExceedDescription());
        feeRuleResponseVo.setExceedPrice(new BigDecimal(feeRuleResponse.getExceedPrice()));

        return feeRuleResponseVo;
    }
}
