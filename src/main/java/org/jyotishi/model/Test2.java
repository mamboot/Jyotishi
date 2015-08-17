package org.jyotishi.model;

import swisseph.*;

public class Test2 {

	static final double AU2km=SweConst.AUNIT/1000;

	  public static void main(String[] p) {
	    SwissEph sw=new SwissEph();
	    SweDate sd=new SweDate(2014,10,11,7+45./60.);

	    // The calculated values will be returned in
	    // this array:
	    double[] res=new double[6];
	    StringBuffer sbErr=new StringBuffer();
	    int flags = SweConst.SEFLG_SPEED |
	                SweConst.SEFLG_TOPOCTR;
	    // Calculate for some place in India:
	    sw.swe_set_topo(87.54,43.12,0);

	    int rc=sw.swe_calc_ut(sd.getJulDay(),
	                          SweConst.SE_MARS,
	                          0,
	                          res,
	                          sbErr);

	    if (sbErr.length()>0) {
	      System.out.println(sbErr.toString());
	    }
	    if (rc==SweConst.ERR) {
	      System.exit(1);
	    }
	    System.out.println(
	        sw.swe_get_planet_name(SweConst.SE_MARS)+":"+
	        "\n\tLongitude:          "+res[0]+
	        "\n\tLatitude:           "+res[1]+
	        "\n\tDistance:           "+res[2]+" AU"+
	        "\n\t                   ("+(res[2]*AU2km)+" km)"+
	        "\n\tLongitudinal speed: "+res[3]+" degs/day");
	  }
}
