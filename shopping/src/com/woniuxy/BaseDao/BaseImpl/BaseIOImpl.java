package com.woniuxy.BaseDao.BaseImpl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.woniuxy.BaseDao.BaseIODao;

public class BaseIOImpl implements BaseIODao {

	@Override
	public <T> ArrayList<T> readAll(File file) {
		// TODO Auto-generated method stub
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
				ObjectInputStream ois = new ObjectInputStream(bis)) {
			return (ArrayList<T>) ois.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch bloc
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
		}
		return null;
	}

	@Override
	public <T> boolean writeAll(ArrayList<T> al, File file) {
		// TODO Auto-generated method stub
		try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
				ObjectOutputStream oos = new ObjectOutputStream(bos)) {
				oos.writeObject(al);
				return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		return false;
	}
}
