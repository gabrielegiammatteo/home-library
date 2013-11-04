/**
 * 
 */
package org.gcube.common.homelibrary.internaltest;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.gcube.common.homelibrary.home.HomeLibrary;
import org.gcube.common.homelibrary.home.HomeManager;
import org.gcube.common.homelibrary.home.HomeManagerFactory;
import org.gcube.common.homelibrary.home.User;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.home.workspace.folder.items.ts.TimeSeries;
import org.gcube.common.homelibrary.util.WorkspaceTreeVisitor;
import org.gcube.common.homelibrary.util.WorkspaceUtil;
import org.gcube.common.scope.api.ScopeProvider;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class TimeSeriesCreator {

	static String resourceRoot = "/org/gcube/portlets/user/homelibrary/testdata/resources/";

	/**
	 * @param args not used.
	 * @throws Exception if an error occurs.
	 */
	public static void main(String[] args) throws Exception {

		
		System.out.println("Welcome to TimeSeries Creator 1.0");

		HomeManagerFactory factory = HomeLibrary.getHomeManagerFactory();

		List<String> scopes = factory.listScopes();
		
		if (scopes.size()==0){
			System.err.println("There are no scopes, please check your configuration");
			System.exit(0);
		}

		Scanner scanner = new Scanner(System.in);

		System.out.println();
		System.out.println("Select a scope:");
		for (int i = 0; i<scopes.size(); i++){
			System.out.println(i+") "+scopes.get(i));
		}

		int selectedScope = scanner.nextInt();

		if (selectedScope<0 || selectedScope >=scopes.size()){
			System.err.println("Wrong selection the number have to be greater than zero and less than "+scopes.size());
			System.exit(0);
		}

		String scope = scopes.get(selectedScope);

		HomeManager manager = factory.getHomeManager();

		List<User> users = manager.getUsers();

		System.out.println();
		System.out.println("Select a user:");
		for (int i = 0; i<users.size(); i++){
			System.out.println(i+") "+users.get(i).getPortalLogin());
		}

		int selectedUser = scanner.nextInt();

		if (selectedUser<0 || selectedUser >=users.size()){
			System.err.println("Wrong selection the number have to be greater than zero and less than "+users.size());
			System.exit(0);
		}

		User user = users.get(selectedUser);

		ScopeProvider.instance.set(scope);
		
		WorkspaceFolder root = manager.getHome(user).getWorkspace().getRoot();

		System.out.println();
		System.out.println("Select the Time Series type:");
		System.out.println("1. Big (large number of row)");
		System.out.println("2. Large (large number of column)");
		System.out.println("3. Small (small numberof row and column)");

		int selectedDimension = scanner.nextInt();

		InputStream compressedTS = null;
		List<String> headerLabels = new LinkedList<String>();
		String name = null;
		int dimension = 0;

		System.out.println();
		switch (selectedDimension) {
			case 1:{
				System.out.println("Creating the big one");
				compressedTS = TestTimeSeriesCreation.class.getResourceAsStream(resourceRoot+"timeseries-big.zip");
				headerLabels.add("Owner");
				headerLabels.add("Flag");
				headerLabels.add("Area");
				headerLabels.add("Year");
				headerLabels.add("Species");
				headerLabels.add("Quantity");
				name = "the big one";
				dimension = 645300;
			}break;

			case 2:{
				System.out.println("Creating the large one");
				compressedTS = TestTimeSeriesCreation.class.getResourceAsStream(resourceRoot+"timeseries-large.zip");

				headerLabels.add("ScientificName");
				headerLabels.add("SpeciesCode");
				headerLabels.add("1969/70");
				headerLabels.add("1970/71");
				headerLabels.add("1971/72");
				headerLabels.add("1972/73");
				headerLabels.add("1973/74");
				headerLabels.add("1974/75");
				headerLabels.add("1975/76");
				headerLabels.add("1976/77");
				headerLabels.add("1977/78");
				headerLabels.add("1978/79");
				headerLabels.add("1979/80");
				headerLabels.add("1980/81");
				headerLabels.add("1981/82");
				headerLabels.add("1982/83");
				headerLabels.add("1983/84");
				headerLabels.add("1984/85");
				headerLabels.add("1985/86");
				headerLabels.add("1986/87");
				headerLabels.add("1987/88");
				headerLabels.add("1988/89");
				headerLabels.add("1989/90");
				headerLabels.add("1990/91");
				headerLabels.add("1991/92");
				headerLabels.add("1992/93");
				headerLabels.add("1993/94");
				headerLabels.add("1994/95");
				headerLabels.add("1995/96");
				headerLabels.add("1996/97");
				headerLabels.add("1997/98");
				headerLabels.add("1998/99");
				headerLabels.add("1999/00");
				headerLabels.add("2000/01");
				headerLabels.add("2001/02");
				headerLabels.add("2002/03");
				headerLabels.add("2003/04");
				headerLabels.add("2004/05");
				
				dimension = 132;

				name = "the large one";
			}break;

			case 3:{
				System.out.println("creeating the small one");
				compressedTS = TestTimeSeriesCreation.class.getResourceAsStream(resourceRoot+"timeseries-small.zip");
				headerLabels.add("Owner");
				headerLabels.add("Flag");
				headerLabels.add("Area");
				headerLabels.add("Species");
				headerLabels.add("Year");
				headerLabels.add("Quantity");
				headerLabels.add("");

				dimension = 98;
				name = "the small one";
			}break;
		}

		name = WorkspaceUtil.getUniqueName(name, root);

		TimeSeries ts = root.createTimeSeries(name, "the best TS", "001010asdsad234324243asdssadas", "TestTS", "federico.defaveri", "A simple TS", "11/11/11", "D4Science", "0002200", "CSV-123", "none", dimension, headerLabels, compressedTS);

		WorkspaceTreeVisitor wtv = new WorkspaceTreeVisitor();
		
		wtv.visitFolderItem(ts);
		
		System.out.println();
		System.out.println("Done.");
	}

}
