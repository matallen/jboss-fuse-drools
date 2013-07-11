package org.jboss.fuse.brms.utils;

import java.io.IOException;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilderFactoryService;
import org.drools.event.process.ProcessEventListener;
import org.drools.event.rule.AgendaEventListener;
import org.drools.event.rule.WorkingMemoryEventListener;
import org.drools.runtime.StatefulKnowledgeSession;

public class DroolsProcessor{
  private static final Logger log=Logger.getLogger(DroolsProcessor.class);
	private String server;
	private String username;
	private String password;
	
	public DroolsProcessor(String server, String username, String password){
		this.server=server;
		this.username=username;
		this.password=password;
	}
	
	@SuppressWarnings("rawtypes")
	public void fire(String packageId, String version, Object fact) throws IOException, ClassNotFoundException{
		RulePackageDownloader downloader=new RulePackageDownloader(server, username, password);
		KnowledgeBase kb=KnowledgeBaseFactory.newKnowledgeBase(/*cfg*/);
	    kb.addKnowledgePackages(downloader.download(packageId, version));
	    StatefulKnowledgeSession s=kb.newStatefulKnowledgeSession();
	    EventListener listener=new EventListener();
	    try{
		    s.addEventListener((WorkingMemoryEventListener)listener);
		    s.addEventListener((AgendaEventListener)listener);
//		    s.addEventListener((ProcessEventListener)listener);
		    
		    if (Collection.class.isAssignableFrom(fact.getClass())){
		    	for(Object subfact:(Collection)fact){
		    		s.insert(subfact);
		    	}
		    }else
		    	s.insert(fact);
		    
		    s.fireAllRules();
		    s.removeEventListener((WorkingMemoryEventListener)listener);
		    
		    log.info("Fired rules:");
		    for(String rule:listener.getRulesFired()){
		      log.info(rule);
		    }
		    
		}finally{
		    s.dispose();
		}
	}
}
