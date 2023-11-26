package com.woniuxy.BaseDao;

import java.io.File;
import java.util.ArrayList;

public interface BaseIODao {

	//¶ÁÈë
	public <T> ArrayList<T> readAll(File file);
	
	//Ð´³ö
	public <T> boolean writeAll(ArrayList<T> al,File file);
 }
