/**
 * Copyright (C) 2011 Adriano Monteiro Marques
 *
 * Author:  Zubair Nabi <zn.zubairnabi@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either Test 2 of the License, or
 * (at your option) any later Test.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 */

package org.umit.icm.mobile.process;

import java.io.IOException;
import java.util.List;

import org.umit.icm.mobile.proto.MessageProtos.Test;
import org.umit.icm.mobile.utils.Constants;
import org.umit.icm.mobile.utils.SDCardReadWrite;

public class TestManager {
	private List<Test> currentTests;
	
	TestManager() {
		currentTests = null;
	}

	public List<Test> getCurrentTests() throws IOException, RuntimeException {
		currentTests = readTests();
		return currentTests;
	}

	public void setCurrentTests(List<Test> currentTests) throws IOException, RuntimeException {
		this.currentTests = currentTests;
		writeTests(currentTests);
	}
	
	private void writeTests(List<Test> tests) throws IOException, RuntimeException {
		SDCardReadWrite.writeTests(Constants.TESTS_DIR, tests);
	}
	
	private List<Test> readTests() throws IOException, RuntimeException {
		return SDCardReadWrite.readTests(Constants.TESTS_DIR, Constants.TESTS_FILE);
	}
	
	public boolean isEqual (TestManager testManager) {
		if (this.equals(testManager))
			return true;
		return false;
	}

}