s.boot;
b = JGBUtil.dirToBuffers("/Users/jesusgollonet/Documents/projects/music-for-pieces-of-steel-against-mylar/dev/build/samples/",s);
n= JGBSoundPlayer.new;

k = b.at(\clamp);
y = b.at(\k1p);
h = b.at(\k1r);
t = b.at(\clap);


n.play(k);
n.play(y);
n.play(h);

(
// groups of notes separated by silence => 3 2 1 2

// additional patterns have:
//   offset: number of beats to skip
//   order of entrance: a filter
// for first section
/*
offsets
0
6
6
0
orders
0, [012345678]
6, 65370142
6, 51623470
0, 51623470

*/


// main pattern
f = [1,1,1,0,1,1,0,1,0,1,1,0];
g = f.rotate(1);
h = f.rotate(2);

Routine{
	inf.do{|i|
		if (f[i%f.size] ==1,{
				n.play(h);
		});
		if (i>(12*4),{
			if (g[i%g.size] ==1,{
				n.play(y);
			});
		});
		if (i>(12*8),{
			if (h[i%h.size] ==1,{
				n.play(k);
			});
		});

		(0.15).wait;
	}

}.play;

)