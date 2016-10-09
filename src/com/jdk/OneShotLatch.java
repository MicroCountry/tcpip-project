package com.jdk;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class OneShotLatch {
	private final Sync sync = new Sync();
	
	public void signal(){
		sync.releaseShared(0);
	}
	
	public void await() throws InterruptedException{
		sync.acquireSharedInterruptibly(0);
	}
	
	private class Sync extends AbstractQueuedSynchronizer{
		/**
		 * 
		 */
		private static final long serialVersionUID = -1091238580269704943L;

		protected int tryAcquireShared(int arg) {
	        return (getState()==1)?1:-1;
	    }
		
		 protected boolean tryReleaseShared(int arg) {
			 setState(1);
			 return true;
		 }
	}
}
