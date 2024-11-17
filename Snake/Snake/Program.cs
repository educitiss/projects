using Snake;

Coord gridDimensions = new Coord(50, 20);
Coord snakePos = new Coord(10, 1);
Random random = new Random();
Coord applePos = new Coord(random.Next(1, gridDimensions.X - 1), random.Next(1,gridDimensions.Y - 1));
int frameDelayMilli = 100;
Direction movementDirection = Direction.Down;
int score = 0;

List<Coord> snakePostHistory = new List<Coord>();
int tailLenght = 1;

while (true)
{
    Console.Clear();
    Console.WriteLine("Score: " + score);
    snakePos.ApplyMovementDirection(movementDirection);

    for (int y = 0; y < gridDimensions.Y; y++)
    {
        for (int x = 0; x < gridDimensions.X; x++)
        {
            Coord currentCoord = new Coord(x, y);

            if (snakePos.Equals(currentCoord) || snakePostHistory.Contains(currentCoord))
                Console.Write("■");
            else if (applePos.Equals(currentCoord))
                Console.Write("a");
            else if (x == 0 || y == 0 || x == gridDimensions.X - 1 || y == gridDimensions.Y - 1)
                Console.Write("#");
            else Console.Write(" ");
        }
        Console.WriteLine();
    }

    if(snakePos.Equals(applePos))
    {
        tailLenght++;
        score++;
        applePos = new Coord(random.Next(1,gridDimensions.X -1), random.Next(1,gridDimensions.Y -1));
    }
    else if(snakePos.X == 0 || snakePos.Y == 0 || 
        snakePos.X == gridDimensions.X -1 || snakePos.Y == gridDimensions.Y - 1 || snakePostHistory.Contains(snakePos))
    {
        score = 0;
        tailLenght = 1;
        snakePos = new Coord(10, 1);
        snakePostHistory.Clear();
        movementDirection = Direction.Down;
        continue;
    }

    snakePostHistory.Add(new Coord(snakePos.X, snakePos.Y));

    if (snakePostHistory.Count > tailLenght)
        snakePostHistory.RemoveAt(0);

    DateTime time = DateTime.Now;

    while((DateTime.Now - time).Milliseconds < frameDelayMilli)
    {
        if (Console.KeyAvailable) 
        {
            ConsoleKey key = Console.ReadKey().Key;

            switch (key) 
            {
                case ConsoleKey.LeftArrow:
                    movementDirection = Direction.Left; 
                    break;
                case ConsoleKey.RightArrow:
                    movementDirection = Direction.Right; 
                    break;
                case ConsoleKey.UpArrow:
                    movementDirection = Direction.Up;
                    break;
                case ConsoleKey.DownArrow:
                    movementDirection = Direction.Down;
                    break;
            }
        }
    }
}