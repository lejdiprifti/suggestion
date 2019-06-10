package com.ikubinfo.project.base;

public interface BaseConverter<M, E> {

	public M toModel(E entity);

	public E toEntity(M model);
}
