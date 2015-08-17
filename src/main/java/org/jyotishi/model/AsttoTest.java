package org.jyotishi.model;

import swisseph.SweConst;
import swisseph.SweDate;
import swisseph.SwissEph;
import swisseph.SwissLib;


public class AsttoTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/* input */
		int year =1981;
		int month = 3;
		int day = 10;
		double longitude =88 + 34/60.0;
		double lattitude =22 + 44/60.0;
		double time = 23+55./60. - 5.5 ;
		
		/*Instances of Utility Classes */
		SwissEph swissEph = new SwissEph();
		SweDate sweDate = new SweDate(year,month,day,time,SweDate.SE_GREG_CAL);
		SwissLib swiislib =new SwissLib();
			
		/*Set some constants*/
		
		swissEph.swe_set_sid_mode(SweConst.SE_SIDM_LAHIRI, 0, 0);
		int houseSys = (int)'E';
		double ayansamaConst = 4500;
		
		
		/*intialize program variables */
		double[] cusps = new double[13];
		double[] acsc = new double[10];
		double[] xx= new double[6];
		double[] xp= new double[6];
		double[] ipl = new double[6];
		StringBuffer serr = new StringBuffer();
		StringBuffer serr1 = new StringBuffer();
		
		/*Temporary variables*/
		double sidetime = swiislib.swe_sidtime(sweDate.getJulDay());
				
		/*Print input Details */
		System.out.println(sweDate);
		
		/*Print Sidereal Time*/
		System.out.print("Sidereal Time:");System.out.println(sidetime);
		// sidetime = acsc[2];
		 System.out.print("Sidereal Time:");System.out.println(sidetime);
		
		/* Calculate Ascendant */
		double ayansama =swissEph.swe_get_ayanamsa_ut(sweDate.getJulDay());
		System.out.print("Ayansama");System.out.println(toDMS(ayansama));
		
		
		ayansamaConst =ayansama/100;
		
		int result = swissEph.swe_houses(sweDate.getJulDay(), SweConst.SEFLG_SIDEREAL, lattitude, longitude, houseSys, cusps,acsc);
		
		System.out.print("Ascendant :"); 	System.out.println(toDMS(acsc[0]));
		
			
		/* calculate position of SUN */
	
		swissEph.swe_calc_ut(sweDate.getJulDay(), SweConst.SE_SUN, SweConst.SE_ECL_NUT, xp, serr);
		swissEph.swe_calc_ut(sweDate.getJulDay(), SweConst.SE_SUN, SweConst.SEFLG_SIDEREAL, xx, serr);
		double position = swissEph.swe_house_pos(sidetime, lattitude,  xp[0], houseSys, xx, serr1);
		
		System.out.print("Sun Postion:");System.out.println(toDMS(position - ayansamaConst));
			
		/* calculate position of MOON */
		
		swissEph.swe_calc_ut(sweDate.getJulDay(), SweConst.SE_MOON, SweConst.SE_ECL_NUT, xp, serr);
		swissEph.swe_calc_ut(sweDate.getJulDay(), SweConst.SE_MOON, SweConst.SEFLG_SIDEREAL, xx, serr);
		position = swissEph.swe_house_pos(sidetime, lattitude,  xp[0], houseSys, xx, serr1);
		
		System.out.print("Moon Postion:");System.out.println(toDMS(position - ayansamaConst));
		
		/* calculate position of MERCURY */
		
		swissEph.swe_calc_ut(sweDate.getJulDay(), SweConst.SE_MERCURY, SweConst.SE_ECL_NUT, xp, serr);
		swissEph.swe_calc_ut(sweDate.getJulDay(), SweConst.SE_MERCURY, SweConst.SEFLG_SIDEREAL, xx, serr);
		position = swissEph.swe_house_pos(sidetime, lattitude,  xp[0], houseSys, xx, serr1);
		
		System.out.print("Mercury Postion:");System.out.println(toDMS(position - ayansamaConst));		
	
		/* calculate position of JUPITOR */
		
		swissEph.swe_calc_ut(sweDate.getJulDay(), SweConst.SE_JUPITER, SweConst.SE_ECL_NUT, xp, serr);
		swissEph.swe_calc_ut(sweDate.getJulDay(), SweConst.SE_JUPITER, SweConst.SEFLG_SIDEREAL, xx, serr);
		position = swissEph.swe_house_pos(sidetime, lattitude,  xp[0], houseSys, xx, serr1);
		System.out.print("Jupitor Postion:");System.out.println(toDMS(position - ayansamaConst));		
			
		/* calculate position of MARS */
		
		swissEph.swe_calc_ut(sweDate.getJulDay(), SweConst.SE_MARS, SweConst.SE_ECL_NUT, xp, serr);
		swissEph.swe_calc_ut(sweDate.getJulDay(), SweConst.SE_MARS, SweConst.SEFLG_SIDEREAL, xx, serr);
		position = swissEph.swe_house_pos(sidetime, lattitude,  xp[0], houseSys, xx, serr1);
		System.out.print("MARS Postion:");System.out.println(toDMS(position - ayansamaConst));		

		/* calculate position of VENUS */
		
		swissEph.swe_calc_ut(sweDate.getJulDay(), SweConst.SE_VENUS, SweConst.SE_ECL_NUT, xp, serr);
		swissEph.swe_calc_ut(sweDate.getJulDay(), SweConst.SE_VENUS, SweConst.SEFLG_SIDEREAL, xx, serr);
		position = swissEph.swe_house_pos(sidetime, lattitude,  xp[0], houseSys, xx, serr1);
		System.out.print("Venus Postion:");System.out.println(toDMS(position - ayansamaConst));	
		
		/* calculate position of SATURN */
		
		swissEph.swe_calc_ut(sweDate.getJulDay(), SweConst.SE_SATURN, SweConst.SE_ECL_NUT, xp, serr);
		swissEph.swe_calc_ut(sweDate.getJulDay(), SweConst.SE_SATURN, SweConst.SEFLG_SIDEREAL, xx, serr);
		position = swissEph.swe_house_pos(sidetime, lattitude,  xp[0], houseSys, xx, serr1);
		System.out.print("Saturn Postion:");System.out.println(toDMS(position - ayansamaConst));	
		
		/* calculate position of RAHU */
		
		swissEph.swe_calc_ut(sweDate.getJulDay(), SweConst.SE_MEAN_NODE , SweConst.SE_ECL_NUT, xp, serr);
		swissEph.swe_calc_ut(sweDate.getJulDay(), SweConst.SE_MEAN_NODE , SweConst.SEFLG_SIDEREAL, xx, serr);
		position = swissEph.swe_house_pos(sidetime, lattitude,  xp[0], houseSys, xx, serr1);
		System.out.print("Rahu Postion:");System.out.println(toDMS(position- ayansamaConst));		

		/* calculate position of KETU */
		
		System.out.print("Kethu Postion:");System.out.println(toDMS(position - ayansamaConst+6));		
		
		}
	
	static String toDMS(double d) {
		d += 0.5/3600./10000.;	// round to 1/1000 of a second
		int deg = (int) d;
		d = (d - deg) * 60;
		int min = (int) d;
		d = (d - min) * 60;
		double sec = d;

		return String.format("%3d° %02d' %07.4f\"", deg, min, sec);
	}
}