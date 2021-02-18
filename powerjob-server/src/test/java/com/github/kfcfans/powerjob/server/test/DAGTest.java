package com.github.kfcfans.powerjob.server.test;

import com.alibaba.fastjson.JSONObject;
import com.github.kfcfans.powerjob.common.InstanceStatus;
import com.github.kfcfans.powerjob.common.model.PEWorkflowDAG;
import com.github.kfcfans.powerjob.common.utils.JsonUtils;
import com.github.kfcfans.powerjob.server.common.utils.WorkflowDAGUtils;
import com.github.kfcfans.powerjob.server.model.WorkflowDAG;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DAG 图算法测试集合
 *
 * @author tjq
 * @since 2020/5/31
 */
public class DAGTest {

    @Test
    public void testDAGUtils() throws Exception {

        List<PEWorkflowDAG.Node> nodes = Lists.newLinkedList();
        List<PEWorkflowDAG.Edge> edges = Lists.newLinkedList();

        // 图1： 1 -> 2 -> 1，理论上报错
        nodes.add(new PEWorkflowDAG.Node(1L, 1L, "1"));
        nodes.add(new PEWorkflowDAG.Node(2L, 2L, "2"));
        edges.add(new PEWorkflowDAG.Edge(1L, 2L));
        edges.add(new PEWorkflowDAG.Edge(2L, 1L));
        System.out.println(WorkflowDAGUtils.valid(new PEWorkflowDAG(nodes, edges)));

        // 图2： 1 -> 2/3 -> 4
        List<PEWorkflowDAG.Node> nodes2 = Lists.newLinkedList();
        List<PEWorkflowDAG.Edge> edges2 = Lists.newLinkedList();

        nodes2.add(new PEWorkflowDAG.Node(1L, 1L, "1"));
        nodes2.add(new PEWorkflowDAG.Node(2L, 2L, "2"));
        nodes2.add(new PEWorkflowDAG.Node(3L, 3L, "3"));
        nodes2.add(new PEWorkflowDAG.Node(4L, 4L, "4"));
        edges2.add(new PEWorkflowDAG.Edge(1L, 2L));
        edges2.add(new PEWorkflowDAG.Edge(1L, 3L));
        edges2.add(new PEWorkflowDAG.Edge(2L, 4L));
        edges2.add(new PEWorkflowDAG.Edge(3L, 4L));

        PEWorkflowDAG validPEDAG = new PEWorkflowDAG(nodes2, edges2);
        System.out.println(WorkflowDAGUtils.valid(validPEDAG));

        WorkflowDAG wfDAG = WorkflowDAGUtils.convert(validPEDAG);
        System.out.println("jackson");
        System.out.println(JsonUtils.toJSONString(wfDAG));

        // Jackson 不知道怎么序列化引用，只能放弃，使用 FastJSON 序列化引用，即 $ref
        WorkflowDAG wfDAGByJackSon = JsonUtils.parseObject(JsonUtils.toJSONString(wfDAG), WorkflowDAG.class);

        System.out.println("fastJson");
        System.out.println(JSONObject.toJSONString(wfDAG));
        WorkflowDAG wfDAGByFastJSON = JSONObject.parseObject(JSONObject.toJSONString(wfDAG), WorkflowDAG.class);

        // 打断点看 reference 关系
        System.out.println(wfDAGByJackSon);
        System.out.println(wfDAGByFastJSON);

        // 测试图三（双顶点） 1 -> 3, 2 -> 4
        List<PEWorkflowDAG.Node> nodes3 = Lists.newLinkedList();
        List<PEWorkflowDAG.Edge> edges3 = Lists.newLinkedList();

        nodes3.add(new PEWorkflowDAG.Node(1L, 1L, "1"));
        nodes3.add(new PEWorkflowDAG.Node(2L, 2L, "2"));
        nodes3.add(new PEWorkflowDAG.Node(3L, 3L, "3"));
        nodes3.add(new PEWorkflowDAG.Node(4L, 4L, "4"));
        edges3.add(new PEWorkflowDAG.Edge(1L, 3L));
        edges3.add(new PEWorkflowDAG.Edge(2L, 4L));

        PEWorkflowDAG multiRootPEDAG = new PEWorkflowDAG(nodes3, edges3);
        System.out.println(WorkflowDAGUtils.valid(multiRootPEDAG));
        WorkflowDAG multiRootDAG = WorkflowDAGUtils.convert(multiRootPEDAG);
        System.out.println(multiRootDAG);

    }

    /**
     * @author Echo009
     * @since 2021/02/07
     */
    @Test
    public void testListReadyNodes1() {
        // 双顶点
        // 1 -> 3
        // 2(x) -> 4 -> 5
        // 6(x) -> 7(x) -> 8(x) -> 4
        //                 8(x) -> 9

        List<PEWorkflowDAG.Node> nodes1 = Lists.newLinkedList();
        List<PEWorkflowDAG.Edge> edges1 = Lists.newLinkedList();

        nodes1.add(new PEWorkflowDAG.Node(1L, 1L, "1"));
        nodes1.add(new PEWorkflowDAG.Node(2L, 2L, "2").setEnable(false));
        nodes1.add(new PEWorkflowDAG.Node(3L, 3L, "3"));
        nodes1.add(new PEWorkflowDAG.Node(4L, 4L, "4"));
        nodes1.add(new PEWorkflowDAG.Node(5L, 5L, "5"));
        nodes1.add(new PEWorkflowDAG.Node(6L, 6L, "6").setEnable(false));
        nodes1.add(new PEWorkflowDAG.Node(7L, 7L, "7").setEnable(false));
        nodes1.add(new PEWorkflowDAG.Node(8L, 8L, "8").setEnable(false));
        nodes1.add(new PEWorkflowDAG.Node(9L, 9L, "9"));
        edges1.add(new PEWorkflowDAG.Edge(1L, 3L));
        edges1.add(new PEWorkflowDAG.Edge(2L, 4L));
        edges1.add(new PEWorkflowDAG.Edge(4L, 5L));
        edges1.add(new PEWorkflowDAG.Edge(4L, 5L));
        edges1.add(new PEWorkflowDAG.Edge(6L, 7L));
        edges1.add(new PEWorkflowDAG.Edge(7L, 8L));
        edges1.add(new PEWorkflowDAG.Edge(8L, 4L));
        edges1.add(new PEWorkflowDAG.Edge(8L, 9L));

        PEWorkflowDAG dag1 = new PEWorkflowDAG(nodes1, edges1);
        List<Long> readyNodeIds1 = WorkflowDAGUtils.listReadyNodes(dag1).stream().map(PEWorkflowDAG.Node::getNodeId).collect(Collectors.toList());

        System.out.println(readyNodeIds1);
        Assert.assertTrue(readyNodeIds1.contains(1L));
        Assert.assertTrue(readyNodeIds1.contains(4L));
        Assert.assertTrue(readyNodeIds1.contains(9L));

    }

    /**
     * @author Echo009
     * @since 2021/02/07
     */
    @Test
    public void testListReadyNodes2() {

        //  注：(x) 代表 enable = false 的节点
        // 测试连续 move
        //  1(x) -> 2(x) -> 3 -> 4 -> 5(x) -> 6

        List<PEWorkflowDAG.Node> nodes = Lists.newLinkedList();
        List<PEWorkflowDAG.Edge> edges = Lists.newLinkedList();

        nodes.add(new PEWorkflowDAG.Node(1L, 1L, "1").setEnable(false));
        nodes.add(new PEWorkflowDAG.Node(2L, 2L, "2").setEnable(false));
        nodes.add(new PEWorkflowDAG.Node(3L, 3L, "3"));
        nodes.add(new PEWorkflowDAG.Node(4L, 4L, "4"));
        nodes.add(new PEWorkflowDAG.Node(5L, 5L, "5").setEnable(false));
        nodes.add(new PEWorkflowDAG.Node(6L, 6L, "6"));
        edges.add(new PEWorkflowDAG.Edge(1L, 2L));
        edges.add(new PEWorkflowDAG.Edge(2L, 3L));
        edges.add(new PEWorkflowDAG.Edge(3L, 4L));
        edges.add(new PEWorkflowDAG.Edge(4L, 5L));
        edges.add(new PEWorkflowDAG.Edge(5L, 6L));

        PEWorkflowDAG dag = new PEWorkflowDAG(nodes, edges);
        List<Long> readyNodeIds2 = WorkflowDAGUtils.listReadyNodes(dag).stream().map(PEWorkflowDAG.Node::getNodeId).collect(Collectors.toList());

        System.out.println(readyNodeIds2);

        Assert.assertEquals(1, readyNodeIds2.size());
        Assert.assertTrue(readyNodeIds2.contains(3L));

    }


    /**
     * @author Echo009
     * @since 2021/02/07
     */
    @Test
    public void testListReadyNodes3() {

        //  注：(x) 代表 enable = false 的节点
        //  复杂 move
        //  1(failed) -> 2(x) -> 4 -> 5(x) -> 6
        //  3(success) -> 4
        //  7 -> 6

        List<PEWorkflowDAG.Node> nodes = Lists.newLinkedList();
        List<PEWorkflowDAG.Edge> edges = Lists.newLinkedList();

        nodes.add(new PEWorkflowDAG.Node(1L, 1L, "1").setStatus(InstanceStatus.FAILED.getV()));
        nodes.add(new PEWorkflowDAG.Node(2L, 2L, "2").setEnable(false));
        nodes.add(new PEWorkflowDAG.Node(3L, 3L, "3").setStatus(InstanceStatus.SUCCEED.getV()));
        nodes.add(new PEWorkflowDAG.Node(4L, 4L, "4"));
        nodes.add(new PEWorkflowDAG.Node(5L, 5L, "5").setEnable(false));
        nodes.add(new PEWorkflowDAG.Node(6L, 6L, "6"));
        nodes.add(new PEWorkflowDAG.Node(7L, 7L, "7"));
        edges.add(new PEWorkflowDAG.Edge(1L, 2L));
        edges.add(new PEWorkflowDAG.Edge(2L, 4L));
        edges.add(new PEWorkflowDAG.Edge(3L, 4L));
        edges.add(new PEWorkflowDAG.Edge(4L, 5L));
        edges.add(new PEWorkflowDAG.Edge(5L, 6L));
        edges.add(new PEWorkflowDAG.Edge(7L, 6L));

        PEWorkflowDAG dag = new PEWorkflowDAG(nodes, edges);
        List<Long> readyNodeIds2 = WorkflowDAGUtils.listReadyNodes(dag).stream().map(PEWorkflowDAG.Node::getNodeId).collect(Collectors.toList());

        System.out.println(readyNodeIds2);

        Assert.assertEquals(2, readyNodeIds2.size());
        Assert.assertTrue(readyNodeIds2.contains(4L));
        Assert.assertTrue(readyNodeIds2.contains(7L));

    }


    /**
     * @author Echo009
     * @since 2021/02/07
     */
    @Test
    public void testListReadyNodes4() {

        //  注：(x) 代表 enable = false 的节点
        //  复杂 move
        //  1(failed) -> 2(x) -> 5 -> 6
        //               3(x) -> 5
        //  1(failed) -> 3(x) -> 4(x) -> 5
        //                       4(x) -> 6

        List<PEWorkflowDAG.Node> nodes = Lists.newLinkedList();
        List<PEWorkflowDAG.Edge> edges = Lists.newLinkedList();

        nodes.add(new PEWorkflowDAG.Node(1L, 1L, "1").setStatus(InstanceStatus.FAILED.getV()));
        nodes.add(new PEWorkflowDAG.Node(2L, 2L, "2").setEnable(false));
        nodes.add(new PEWorkflowDAG.Node(3L, 3L, "3").setEnable(false));
        nodes.add(new PEWorkflowDAG.Node(4L, 4L, "4").setEnable(false));
        nodes.add(new PEWorkflowDAG.Node(5L, 5L, "5"));
        nodes.add(new PEWorkflowDAG.Node(6L, 6L, "6"));
        edges.add(new PEWorkflowDAG.Edge(1L, 2L));
        edges.add(new PEWorkflowDAG.Edge(2L, 5L));
        edges.add(new PEWorkflowDAG.Edge(5L, 6L));
        edges.add(new PEWorkflowDAG.Edge(1L, 3L));
        edges.add(new PEWorkflowDAG.Edge(3L, 4L));
        edges.add(new PEWorkflowDAG.Edge(3L, 5L));
        edges.add(new PEWorkflowDAG.Edge(4L, 5L));

        PEWorkflowDAG dag = new PEWorkflowDAG(nodes, edges);
        List<Long> readyNodeIds2 = WorkflowDAGUtils.listReadyNodes(dag).stream().map(PEWorkflowDAG.Node::getNodeId).collect(Collectors.toList());

        System.out.println(readyNodeIds2);

        Assert.assertEquals(1, readyNodeIds2.size());
        Assert.assertTrue(readyNodeIds2.contains(5L));

    }


    /**
     * @author Echo009
     * @since 2021/02/07
     */
    @Test
    public void testListReadyNodes5() {

        //  注：(x) 代表 enable = false 的节点
        //  复杂 move
        //  1(failed) -> 2(x) -> 5 -> 6
        //               3(x) -> 5
        //  1(failed) -> 3(x) -> 4(x) -> 5
        //                       4(x) -> 6
        //                       4(x) -> 7

        List<PEWorkflowDAG.Node> nodes = Lists.newLinkedList();
        List<PEWorkflowDAG.Edge> edges = Lists.newLinkedList();

        nodes.add(new PEWorkflowDAG.Node(1L, 1L, "1").setStatus(InstanceStatus.FAILED.getV()));
        nodes.add(new PEWorkflowDAG.Node(2L, 2L, "2").setEnable(false));
        nodes.add(new PEWorkflowDAG.Node(3L, 3L, "3").setEnable(false));
        nodes.add(new PEWorkflowDAG.Node(4L, 4L, "4").setEnable(false));
        nodes.add(new PEWorkflowDAG.Node(5L, 5L, "5"));
        nodes.add(new PEWorkflowDAG.Node(6L, 6L, "6"));
        nodes.add(new PEWorkflowDAG.Node(7L, 7L, "7"));
        edges.add(new PEWorkflowDAG.Edge(1L, 2L));
        edges.add(new PEWorkflowDAG.Edge(2L, 5L));
        edges.add(new PEWorkflowDAG.Edge(5L, 6L));
        edges.add(new PEWorkflowDAG.Edge(1L, 3L));
        edges.add(new PEWorkflowDAG.Edge(3L, 4L));
        edges.add(new PEWorkflowDAG.Edge(3L, 5L));
        edges.add(new PEWorkflowDAG.Edge(4L, 5L));
        edges.add(new PEWorkflowDAG.Edge(4L, 7L));

        PEWorkflowDAG dag = new PEWorkflowDAG(nodes, edges);
        List<Long> readyNodeIds2 = WorkflowDAGUtils.listReadyNodes(dag).stream().map(PEWorkflowDAG.Node::getNodeId).collect(Collectors.toList());

        System.out.println(readyNodeIds2);

        Assert.assertEquals(2, readyNodeIds2.size());
        Assert.assertTrue(readyNodeIds2.contains(5L));
        Assert.assertTrue(readyNodeIds2.contains(7L));

    }

}
