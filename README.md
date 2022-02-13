# animal-encyclopedia

I went out of my way and built an animal encyclopedia with multi-modules setup by using both cat and dog api.

# Thoughts

When it comes to scalability, multi-modules usually works fine, although there will be a downside later which is increased build time but that's when the app has grown large with few hundreds of modules. Other good points includes easier to apply clean architecture practices, separation of concerns.

I'm comfortable with multi-modules since I'm used to work in a start-up with hundreds of engineers spanned across different teams/departments/region.

I added favorite feature for a way to demonstrate reactive programming for the concurrency and synchronisation.

Try look around in `Cat` category instead since I found out `Dog` category api doesn't have much data as `Cat` for some reason.

# Things I didn't do / Wanted to do

1. Break down ui to feature ui modules
1. Instrumented test
1. Only have unit test in 1 module, it is roughly the same as others anyway, since this is a test i didn't include all of it
1. Android Room for data persistency, I'm using "in-memory" for now
1. DataSource pattern
