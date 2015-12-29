package com.handu.open.dubbo.monitor.domain;

import java.io.Serializable;

public class Application implements Serializable {

	 /**
		 * 
		 */
		private static final long serialVersionUID = -5468082346847524044L;

		private Long id;
		
	    private String name ;

		/**
		 * @return the id
		 */
		public Long getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(Long id) {
			this.id = id;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
	    
			

}
