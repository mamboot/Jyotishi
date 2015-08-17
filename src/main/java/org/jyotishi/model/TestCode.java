package org.jyotishi.model;

import swisseph.*;

public class TestCode {

	/**
	* The method to determine ayanamsha value:
	*/
	private static final int SID_METHOD = SweConst.SE_SIDM_LAHIRI;


	public static void main( String[] args) {
		// Input data:
		int year = 1981;
		int month = 3;
		int day = 10;
		double longitude =88 + 34/60.0;	// Chennai
		double latitude =22 + 44/60.0;
		double hour = 23+55./60. - 5.5; // IST

		/*Instances of utility classes */
		SwissEph sw = new SwissEph("./ephe");
		SweDate sd = new SweDate(year,month,day,hour);

		// Set sidereal mode:
		sw.swe_set_sid_mode(SID_METHOD, 0, 0);

		// Some required variables:
		double[] cusps = new double[13];
		double[] acsc = new double[10];
		double[] xp= new double[6];
		StringBuffer serr = new StringBuffer();

		// Print input details:
		System.out.println("Date: " + sd);
		System.out.println("Location: " +
				toDMS(longitude) + (longitude > 0 ? "E" : "W") +
				" / " +
				toDMS(latitude) + (latitude > 0 ? "N" : "S"));

		// Get and print ayanamsa value for info:
		double ayanamsa = sw.swe_get_ayanamsa_ut(sd.getJulDay());
		System.out.println("Ayanamsa: " + toDMS(ayanamsa) + " (" + sw.swe_get_ayanamsa_name(SID_METHOD) + ")");

		// Get and print lagna:
		int flags = SweConst.SEFLG_SIDEREAL;
		int result = sw.swe_houses(sd.getJulDay(),
				flags,
				latitude,
				longitude,
				'P',
				cusps,
				acsc);
		System.out.println("Ascendant: " + toDMS(acsc[0]));

		int ascSign = (int)(acsc[0] / 30) + 1;

		// Calculate all planets:
		int[] planets = { SweConst.SE_SUN,
				SweConst.SE_MOON,
				SweConst.SE_MARS,
				SweConst.SE_MERCURY,
				SweConst.SE_JUPITER,
				SweConst.SE_VENUS,
				SweConst.SE_SATURN,
				SweConst.SE_TRUE_NODE,
				SweConst.SE_NEPTUNE,
				SweConst.SE_PLUTO,
				SweConst.SE_URANUS};	// Some systems prefer SE_MEAN_NODE

		flags = SweConst.SEFLG_SWIEPH |		// fastest method, requires data files
				SweConst.SEFLG_SIDEREAL |	// sidereal zodiac
				SweConst.SEFLG_NONUT |		// will be set automatically for sidereal calculations, if not set here
				SweConst.SEFLG_SPEED;		// to determine retrograde vs. direct motion
		int sign;
		int house;
		boolean retrograde = false;

		for(int p = 0; p < planets.length; p++) {
			int planet = planets[p];
			String planetName = sw.swe_get_planet_name(planet);
			int ret = sw.swe_calc_ut(sd.getJulDay(),
					planet,
					flags,
					xp,
					serr);

			if (ret != flags) {
				if (serr.length() > 0) {
					System.err.println("Warning: " + serr);
				} else {
					System.err.println(
							String.format("Warning, different flags used (0x%x)", ret));
				}
			}

			sign = (int)(xp[0] / 30) + 1;
			house = (sign + 12 - ascSign) % 12 +1;
			retrograde = (xp[3] < 0);

			System.out.printf("%-12s: %s %c; sign: %2d; house %2d; degree in house: %s\n",
					 planetName, toDMS(xp[0]), (retrograde ? 'R' : 'D'), sign, house, toDMS(xp[0] % 30));
		}
		// KETU
		xp[0] = (xp[0] + 180.0) % 360;
		String planetName = "Ketu";

		sign = (int)(xp[0] / 30) + 1;
		house = (sign + 12 - ascSign) % 12 +1;

		System.out.printf("%-12s: %s %c; sign: %2d; house %2d; degree in house: %s\n",
				 planetName, toDMS(xp[0]), (retrograde ? 'R' : 'D'), sign, house, toDMS(xp[0] % 30));
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
