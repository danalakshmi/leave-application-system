package com.paul.workflow.service.command;
/**
 * 应用回调接口
 * @author Paul
 *
 * @param <T>
 * @param <V>
 */
public interface AppServiceCommand<T extends Object,V> {
	public T execute(V obj);
}