package com.sail.mobile.analyzer.googleplay.commmon;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class FileUtil
{
	public static void closeFile(BufferedReader br)
	{
		try
		{
			if (br != null)
			{
				br.close();
			}
		} catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

	public static void closeFile(FileInputStream f1InputStream)
	{
		if (f1InputStream != null)
		{
			try
			{
				f1InputStream.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void closeFile(BufferedInputStream bufFirstInput)
	{
		if (bufFirstInput != null)
		{
			try
			{
				bufFirstInput.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}		
	}

	public static void closeFile(PrintWriter emergencyReleasesIntersectionWriter)
	{
		emergencyReleasesIntersectionWriter.close();
	}
}
