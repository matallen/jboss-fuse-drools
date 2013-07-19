package org.jboss.fuse.drools.utils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.event.rule.AgendaEventListener;
import org.drools.event.rule.WorkingMemoryEventListener;
import org.drools.runtime.StatefulKnowledgeSession;

public class DroolsProcessor {
  private static final Logger log = Logger.getLogger(DroolsProcessor.class);
  private String server;
  private String username;
  private String password;

  public DroolsProcessor(String server, String username, String password) throws IOException {
    this.server = server;
    this.username = username;
    this.password = password;
  }

  public StatefulKnowledgeSession getStatefulKnowledgeSession(String packageId, String version) throws IOException, ClassNotFoundException {
    RulePackageDownloader downloader = new RulePackageDownloader(server, username, password);
    KnowledgeBase kb = KnowledgeBaseFactory.newKnowledgeBase(/* cfg */);
    kb.addKnowledgePackages(downloader.download(packageId, version));
    StatefulKnowledgeSession s = kb.newStatefulKnowledgeSession();
    return s;
  }

  public void fire(StatefulKnowledgeSession session, String packageId, String version, Object fact) throws IOException, ClassNotFoundException {
    EventListener listener = new EventListener();
    try {
      session.addEventListener((WorkingMemoryEventListener) listener);
      session.addEventListener((AgendaEventListener) listener);
//      session.addEventListener((ProcessEventListener) listener);

      if (Collection.class.isAssignableFrom(fact.getClass())) {
        for (Object subfact : (Collection) fact) {
          session.insert(subfact);
        }
      } else
        session.insert(fact);

      session.fireAllRules();
      session.removeEventListener((WorkingMemoryEventListener) listener);

      if (listener.getRulesFired().size() > 0) {
        log.info("Rules fired:");
        for (String rule : listener.getRulesFired())
          log.info("  " + rule);
      } else
        log.info("NO rules fired");

    } finally {
      session.dispose();
    }
  }

  public void fire(String packageId, String version, Object fact) throws IOException, ClassNotFoundException {
    RulePackageDownloader downloader = new RulePackageDownloader(server, username, password);
    KnowledgeBase kb = KnowledgeBaseFactory.newKnowledgeBase(/* cfg */);
    kb.addKnowledgePackages(downloader.download(packageId, version));
    StatefulKnowledgeSession session = getStatefulKnowledgeSession(packageId, version);
    fire(session, packageId, version, fact);
  }
}
