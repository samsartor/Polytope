package net.eekysam.jpolytope.geometric;

import java.util.HashMap;
import java.util.Set;

import net.eekysam.jpolytope.general.PolytopeGraph;
import net.eekysam.jpolytope.geometric.dat.IGeoDat;
import net.eekysam.jpolytope.geometric.dat.IPointDat;

public class Polytope
{
	public final int dimension;
	public final PolytopeGraph<IGeoDat> geo;
	
	public Polytope(int dimension)
	{
		this(new PolytopeGraph<>(dimension));
	}
	
	public Polytope(PolytopeGraph<IGeoDat> geo)
	{
		this.dimension = geo.dimension;
		this.geo = geo;
	}
	
	@Override
	public Polytope clone()
	{
		Polytope clone = new Polytope(this.geo.clone());
		HashMap<Integer, Set<PolytopeGraph<IGeoDat>>> all = clone.geo.getElements();
		for (Set<PolytopeGraph<IGeoDat>> dimset : all.values())
		{
			for (PolytopeGraph<IGeoDat> elem : dimset)
			{
				if (elem.data != null)
				{
					elem.data = elem.data.clone();
				}
			}
		}
		return clone;
	}
	
	public void transform(TransformMatrix matrix)
	{
		for (PolytopeGraph<IGeoDat> vert : this.geo.getElements(0))
		{
			if (vert.data instanceof IPointDat)
			{
				((IPointDat) vert.data).transform(matrix);
			}
		}
	}
	
	public void scale(double scale)
	{
		TransformMatrix matrix = new TransformMatrix(this.dimension);
		matrix.scale(scale);
		this.transform(matrix);
	}
}
