package cn.demi.base.system.mapper;

public interface IMapper<P> {
	void add(P p);

	void delete(String id);

	void list();

	void update(P p);
}
