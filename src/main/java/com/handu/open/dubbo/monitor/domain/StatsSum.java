/**
 * Copyright 2006-2015 handu.com
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.handu.open.dubbo.monitor.domain;

public class StatsSum implements Comparable<StatsSum> {

	private String name;
	private long numProvider;
	private long numConsumer;

	private long elapsedProvider;
	private long elapsedConsumer;

	/**
	 * @return the elapsedProvider
	 */
	public long getElapsedProvider() {
		return elapsedProvider;
	}

	/**
	 * @param elapsedProvider the elapsedProvider to set
	 */
	public void setElapsedProvider(long elapsedProvider) {
		this.elapsedProvider = elapsedProvider;
	}

	/**
	 * @return the elapsedConsumer
	 */
	public long getElapsedConsumer() {
		return elapsedConsumer;
	}

	/**
	 * @param elapsedConsumer the elapsedConsumer to set
	 */
	public void setElapsedConsumer(long elapsedConsumer) {
		this.elapsedConsumer = elapsedConsumer;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the numProvider
	 */
	public long getNumProvider() {
		return numProvider;
	}

	/**
	 * @param numProvider
	 *            the numProvider to set
	 */
	public void setNumProvider(long numProvider) {
		this.numProvider = numProvider;
	}

	/**
	 * @return the numConsumer
	 */
	public long getNumConsumer() {
		return numConsumer;
	}

	/**
	 * @param numConsumer
	 *            the numConsumer to set
	 */
	public void setNumConsumer(long numConsumer) {
		this.numConsumer = numConsumer;
	}

	public int compareTo(StatsSum o) {
		if(o.getNumProvider() > this.numProvider)
			return 1;
		if(o.getNumProvider() < this.numProvider)
			return -1;
		
		return 0;
	}
}