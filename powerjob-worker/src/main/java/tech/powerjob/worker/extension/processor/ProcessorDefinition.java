package tech.powerjob.worker.extension.processor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Objects;

/**
 * 处理器定义
 *
 * @author tjq
 * @since 2023/1/17
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ProcessorDefinition implements Serializable {

    /**
     * 后台配置的处理器类型
     */
    private String processorType;
    /**
     * 后台配置的处理器信息
     */
    private String processorInfo;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProcessorDefinition that = (ProcessorDefinition) o;
        return Objects.equals(processorType, that.processorType) && Objects.equals(processorInfo, that.processorInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(processorType, processorInfo);
    }
}
