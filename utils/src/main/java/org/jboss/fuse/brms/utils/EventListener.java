package org.jboss.fuse.brms.utils;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.drools.event.process.ProcessCompletedEvent;
import org.drools.event.process.ProcessEventListener;
import org.drools.event.process.ProcessNodeLeftEvent;
import org.drools.event.process.ProcessNodeTriggeredEvent;
import org.drools.event.process.ProcessStartedEvent;
import org.drools.event.process.ProcessVariableChangedEvent;
import org.drools.event.rule.ActivationCancelledEvent;
import org.drools.event.rule.ActivationCreatedEvent;
import org.drools.event.rule.AfterActivationFiredEvent;
import org.drools.event.rule.AgendaEventListener;
import org.drools.event.rule.AgendaGroupPoppedEvent;
import org.drools.event.rule.AgendaGroupPushedEvent;
import org.drools.event.rule.BeforeActivationFiredEvent;
import org.drools.event.rule.ObjectInsertedEvent;
import org.drools.event.rule.ObjectRetractedEvent;
import org.drools.event.rule.ObjectUpdatedEvent;
import org.drools.event.rule.RuleFlowGroupActivatedEvent;
import org.drools.event.rule.RuleFlowGroupDeactivatedEvent;
import org.drools.event.rule.WorkingMemoryEventListener;

public class EventListener implements AgendaEventListener, WorkingMemoryEventListener, ProcessEventListener {
  private final Logger log = Logger.getLogger(EventListener.class);
  private ArrayList<String> rulesFired = new ArrayList<String>();
  private int numRulesFired = 0;

  public void activationCancelled(ActivationCancelledEvent event) {
    if (log.isDebugEnabled())
      log.debug("ACTIVATION CANCELLED       : " + event.getActivation().getRule().getName());
  }

  public void activationCreated(ActivationCreatedEvent event) {
    if (log.isDebugEnabled())
      log.debug("ACTIVATION CREATED         : " + event.getActivation().getRule().getName());
  }

  public void afterActivationFired(AfterActivationFiredEvent event) {
    if (log.isDebugEnabled())
      log.debug("AFTER RULE FIRED           : " + event.getActivation().getRule().getName());
    rulesFired.add(Integer.toString(numRulesFired + 1) + "\t: " + event.getActivation().getRule().getName());
    numRulesFired++;
  }

  public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
    if (log.isDebugEnabled())
      log.debug("AGENDA GROUP POPPED        : " + event.getAgendaGroup().getName());
  }

  public void agendaGroupPushed(AgendaGroupPushedEvent event) {
    if (log.isDebugEnabled())
      log.debug("AGENDA GROUP PUSHED        : " + event.getAgendaGroup().getName());
  }

  public void beforeActivationFired(BeforeActivationFiredEvent activation) {
    if (log.isTraceEnabled())
      log.debug("BEFORE RULE FIRED          : " + activation.getActivation().getRule().getName());
  }

  public void objectInserted(ObjectInsertedEvent event) {
    if (log.isDebugEnabled())
      log.debug("INSERTED                   : " + event.getObject());
  }

  public void objectRetracted(ObjectRetractedEvent event) {
    if (log.isDebugEnabled())
      log.debug("RETRACTED                  : " + event.getOldObject());
  }

  public void objectUpdated(ObjectUpdatedEvent event) {
    if (log.isDebugEnabled())
      log.debug("UPDATED                    : " + event.getObject());
  }

  public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent arg0) {}
  public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent arg0) {}
  public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent arg0) {}
  public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent arg0) {}

  public int getNumRulesFired() {
    return numRulesFired;
  }

  public ArrayList<String> getRulesFired() {
    return rulesFired;
  }

  @Override public void afterNodeLeft(ProcessNodeLeftEvent arg0) {}
  @Override public void afterNodeTriggered(ProcessNodeTriggeredEvent arg0) {}
  @Override public void afterProcessCompleted(ProcessCompletedEvent arg0) {}
  @Override public void afterProcessStarted(ProcessStartedEvent arg0) {}
  @Override public void afterVariableChanged(ProcessVariableChangedEvent arg0) {}
  @Override public void beforeNodeLeft(ProcessNodeLeftEvent arg0) {}
  @Override public void beforeNodeTriggered(ProcessNodeTriggeredEvent arg0) {}
  @Override public void beforeProcessCompleted(ProcessCompletedEvent arg0) {}
  @Override public void beforeProcessStarted(ProcessStartedEvent arg0) {}
  @Override public void beforeVariableChanged(ProcessVariableChangedEvent arg0) {
  }
}
