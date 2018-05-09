package com.nobodyhub.payroll.core.context.abstr;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.formula.common.Formula;
import com.nobodyhub.payroll.core.formula.normal.NormalFormula;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Ryan
 */
@Getter
public abstract class FormulaContainer<T extends Formula> {
    /**
     * full formula list involved in this context
     */
    protected List<T> formulas = Lists.newLinkedList();
    /**
     * Map from target item id to formula
     * several formulas could be applied to the same items id in different period
     */
    protected Map<String, List<T>> formulaMap = Maps.newHashMap();

    /**
     * assign different priority to formula according to inter-dependencies on items
     */
    public void prioritize() {
        Map<String, Node<T>> nodes = Maps.newHashMap();
        for (T curFormula : formulas) {
            Node<T> curNode = new Node<>(curFormula);
            nodes.put(curFormula.getFormulaId(), curNode);
            Set<String> requiredItems = curFormula.getRequiredItems();
            for (String itemId : requiredItems) {
                List<T> precedeFormulas = formulaMap.get(itemId);
                for (T preFormula : precedeFormulas) {
                    Node<T> preNode = nodes.get(preFormula.getFormulaId());
                    if (preNode == null) {
                        preNode = new Node<>(preFormula);
                        nodes.put(preFormula.getFormulaId(), preNode);
                    }
                    curNode.addPreNode(preNode);
                    preNode.moveForward();
                }
            }
        }
        //sort based on the priority
        Collections.sort(formulas);
    }

    /**
     * Hash and Equals based on {@link NormalFormula#formulaId}
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

            return formula != null ? formula.getFormulaId().equals(node.formula.getFormulaId()) : node.formula == null;
        }

        @Override
        public int hashCode() {
            return formula != null ? formula.getFormulaId().hashCode() : 0;
        }
    }
}