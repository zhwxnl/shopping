package com.woniuxy.BaseDao;

import java.io.File;
import java.util.ArrayList;

public interface BaseIODao {

	//����
	public <T> ArrayList<T> readAll(File file);
	
	//д��
	public <T> boolean writeAll(ArrayList<T> al,File file);
 }
