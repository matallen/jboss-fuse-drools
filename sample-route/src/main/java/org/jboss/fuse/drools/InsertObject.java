package org.jboss.fuse.drools;

import org.apache.camel.Exchange;

public class InsertObject {
  public void insert(Exchange exchange){
    Person p=new Person();
    p.setHuman(true);
    exchange.getIn().setBody(p);
  }
}
