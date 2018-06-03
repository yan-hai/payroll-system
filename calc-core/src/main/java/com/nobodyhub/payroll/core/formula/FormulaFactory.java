package com.nobodyhub.payroll.core.formula;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.common.Factory;
import com.nobodyhub.payroll.core.formula.common.Formula;
import com.nobodyhub.payroll.core.formula.normal.NormalFormula;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Ryan
 */
public abstract class FormulaFactory<T extends Formula> extends Factory<T> {
    /**
     * Factory returns builder to generate item instance
     */
    protected final ItemBuilderFactory itemBuilderFactory;

    protected FormulaFactory(ItemBuilderFactory itemBuilderFactory) {
        this.itemBuilderFactory = itemBuilderFactory;
        initContents();
    }

    /**
     * get a list of formulas whose order is based on the interdependence of
     * target items
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<T> getPrioritizedFormulas() {
        List<T> formulas = Lists.newArrayList();
        //Map from target item id to formula
        Map<String, List<T>> formulaMap = Maps.newHashMap();

        for (T formula : contents.values()) {
            formulas.add(formula);
            List<T> formulaList = formulaMap.get(formula.getTargetItemId());
            if (formulaList == null) {
                formulaList = Lists.newArrayList();
            }
            formulaList.add(formula);
            formulaMap.put(formula.getTargetItemId(), formulaList);
        }
        //assign different priority to formula
        Map<String, Node<T>> nodes = Maps.newHashMap();
        for (T curFormula : formulas) {
            Node<T> curNode = new Node<>(curFormula);
            nodes.put(curFormula.getId(), curNode);
            Set<String> requiredItems = curFormula.getRequiredItems();
            // find if any formula for required items
            for (String itemId : requiredItems) {
                // those formulas are precede formulas
                List<T> preFormulas = formulaMap.get(itemId);
                if (preFormulas != null) {
                    for (T preFormula : preFormulas) {
                        // add precede formula to preNodes
                        Node<T> preNode = nodes.get(preFormula.getId());
                        if (preNode == null) {
                            preNode = new Node<>(preFormula);
                            nodes.put(preFormula.getId(), preNode);
                        }
                        curNode.addPreNode(preNode);
                        // set a higher priority for preNode
                        preNode.moveForward();
                    }
                }
            }
        }
        //sort based on the priority
        Collections.sort(formulas);
        return formulas;
    }

    /**
     * Hash and Equals based on {@link NormalFormula#id}
     */
    @RequiredArgsConstructor
    private static class Node<T extends Formula> {
        final T formula;
        Set<Node> preNodes = Sets.newHashSet();

        public void moveForward() {
            formula.setPriority(formula.getPriority() - 1);
            for (Node node : preNodes) {
                node.moveForward();
            }
        }

        public void addPreNode(Node preNode) {
            preNodes.add(preNode);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Node node = (Node) o;

            return formula != null ? formula.getId().equals(node.formula.getId()) : node.formula == null;
        }

        @Override
        public int hashCode() {
            return formula != null ? formula.getId().hashCode() : 0;
        }
    }
}
